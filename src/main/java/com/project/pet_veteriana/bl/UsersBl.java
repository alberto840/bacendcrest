package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Rol;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UsersBl {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private PetsRepository petsRepository;

    // Crear usuario con imagen
    @Transactional
    public UsersDto createUserWithImage(UsersDto usersDto, MultipartFile file) throws Exception {
        // Obtener el rol correspondiente usando el rolId
        Rol rol = rolRepository.findById(usersDto.getRolId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Encriptar la contraseña antes de guardarla
        String encryptedPassword = BCrypt.hashpw(usersDto.getPassword(), BCrypt.gensalt());

        // Subir la imagen a MinIO y guardar en la base de datos
        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        // Crear un nuevo usuario con el ID de la imagen y el rol asociado
        Users user = new Users();
        user.setName(usersDto.getName());
        user.setEmail(usersDto.getEmail());
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setPassword(encryptedPassword);
        user.setLocation(usersDto.getLocation());
        user.setPreferredLanguage(usersDto.getPreferredLanguage());
        user.setStatus(usersDto.getStatus());
        user.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        user.setRol(rol); // Asignar el rol al usuario

        Users savedUser = usersRepository.save(user);
        return mapToDto(savedUser);
    }

    // Obtener todos los usuarios
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Obtener un usuario por su ID
    public Optional<UsersDto> getUserById(Integer userId) {
        Optional<Users> user = usersRepository.findById(userId);
        return user.map(this::mapToDto);
    }

    // Actualizar un usuario con imagen
    @Transactional
    public Optional<UsersDto> updateUserWithImage(Integer userId, UsersDto usersDto, MultipartFile file) throws Exception {
        Optional<Users> existingUser = usersRepository.findById(userId);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();

            if (file != null && !file.isEmpty()) {
                ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
                user.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
            }

            // Verificar y actualizar el rol si se ha enviado uno nuevo
            if (usersDto.getRolId() != null) {
                Rol rol = rolRepository.findById(usersDto.getRolId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));
                user.setRol(rol);
            }

            user.setName(usersDto.getName());
            user.setEmail(usersDto.getEmail());
            user.setPhoneNumber(usersDto.getPhoneNumber());
            user.setLocation(usersDto.getLocation());
            user.setPreferredLanguage(usersDto.getPreferredLanguage());
            user.setLastLogin(usersDto.getLastLogin() != null ? usersDto.getLastLogin() : user.getLastLogin());
            user.setStatus(usersDto.getStatus());

            Users updatedUser = usersRepository.save(user);
            return Optional.of(mapToDto(updatedUser));
        }
        return Optional.empty();
    }

    // Eliminar un usuario
    @Transactional
    public boolean deleteUser(Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Eliminar transacciones asociadas antes de borrar el usuario
            transactionHistoryRepository.deleteByUser(user);

            // Eliminar proveedores asociados antes de borrar el usuario
            providersRepository.deleteByUser(user);

            petsRepository.deleteByUser(user);

            // Desvincular la imagen antes de eliminarla
            if (user.getImage() != null) {
                try {
                    imagesS3Bl.deleteFile(user.getImage().getImageId());
                } catch (Exception e) {
                    throw new RuntimeException("Error deleting associated image from MinIO", e);
                }
                user.setImage(null);
                usersRepository.save(user); // Guardar la desvinculación antes de eliminar el usuario
            }

            // Finalmente, eliminamos el usuario
            usersRepository.delete(user);
            return true;
        }

        return false;
    }






    // Cambiar contraseña
    public void changePassword(String email, String oldPassword, String newPassword) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verificar si la contraseña antigua es correcta
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Encriptar la nueva contraseña
        String encryptedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Actualizar la contraseña
        user.setPassword(encryptedNewPassword);  // Aquí se guarda la nueva contraseña encriptada
        usersRepository.save(user);
    }

    private UsersDto mapToDto(Users user) {
        String imageUrl = null;
        if (user.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(user.getImage().getFileName());
        }

        // Obtener providerId desde la tabla Providers si existe
        Integer providerId = null;
        // Llamar al repositorio para verificar si el usuario tiene un proveedor asociado
        Optional<Providers> provider = providersRepository.findByUser_UserId(user.getUserId());

        // Si existe el proveedor, asignar el providerId
        if (provider.isPresent()) {
            providerId = provider.get().getProviderId(); // Aquí obtenemos el providerId desde la tabla Providers
        }

        return new UsersDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null, // No pasamos password por seguridad
                user.getPhoneNumber(),
                user.getLocation(),
                user.getPreferredLanguage(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getStatus(),
                user.getRol() != null ? user.getRol().getRolId() : null,  // Si rolId no es null, pasamos el valor
                user.getImage() != null ? user.getImage().getImageId() : null, // Obtener imageId
                imageUrl,  // Si tiene imagen, asignar la URL
                providerId // Asignamos el providerId si es un proveedor
        );
    }


}
