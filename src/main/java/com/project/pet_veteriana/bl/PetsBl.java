package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.PetsDto;
import com.project.pet_veteriana.entity.ImageS3;
import com.project.pet_veteriana.entity.Pets;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.PetsRepository;
import com.project.pet_veteriana.repository.ReservationsRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetsBl {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Autowired
    private ReservationsRepository reservationsRepository;

    // Crear una nueva mascota con imagen
    @Transactional
    public PetsDto createPetWithImage(PetsDto petsDto, MultipartFile file) throws Exception {
        Optional<Users> userOptional = usersRepository.findById(petsDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Subir imagen a MinIO
        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        // Crear objeto mascota
        Pets pet = new Pets();
        pet.setPetName(petsDto.getPetName());
        pet.setPetBreed(petsDto.getPetBreed());
        pet.setPetAge(petsDto.getPetAge());
        pet.setBirthDate(petsDto.getBirthDate());
        pet.setSpecies(petsDto.getSpecies());
        pet.setWeight(petsDto.getWeight());
        pet.setHeight(petsDto.getHeight());
        pet.setGender(petsDto.getGender());
        pet.setAllergies(petsDto.getAllergies());
        pet.setBehaviorNotes(petsDto.getBehaviorNotes());
        pet.setCreatedAt(LocalDateTime.now());
        pet.setUser(userOptional.get());
        pet.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));

        Pets savedPet = petsRepository.save(pet);
        return convertToDto(savedPet);
    }

    // Obtener todas las mascotas
    public List<PetsDto> getAllPets() {
        List<Pets> pets = petsRepository.findAll();
        return pets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener mascota por ID
    public Optional<PetsDto> getPetById(Integer petId) {
        Optional<Pets> pet = petsRepository.findById(petId);
        return pet.map(this::convertToDto);
    }

    // Actualizar una mascota con imagen
    @Transactional
    public Optional<PetsDto> updatePetWithImage(Integer petId, PetsDto petsDto, MultipartFile file) throws Exception {
        Optional<Pets> existingPet = petsRepository.findById(petId);
        if (existingPet.isEmpty()) {
            throw new IllegalArgumentException("Mascota no encontrada");
        }

        Pets pet = existingPet.get();

        // Subir nueva imagen si se proporciona
        if (file != null && !file.isEmpty()) {
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
            pet.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        }

        pet.setPetName(petsDto.getPetName());
        pet.setPetBreed(petsDto.getPetBreed());
        pet.setPetAge(petsDto.getPetAge());
        pet.setBirthDate(petsDto.getBirthDate());
        pet.setSpecies(petsDto.getSpecies());
        pet.setWeight(petsDto.getWeight());
        pet.setHeight(petsDto.getHeight());
        pet.setGender(petsDto.getGender());
        pet.setAllergies(petsDto.getAllergies());
        pet.setBehaviorNotes(petsDto.getBehaviorNotes());

        Pets updatedPet = petsRepository.save(pet);
        return Optional.of(convertToDto(updatedPet));
    }

    @Transactional
    // Eliminar una mascota
    public boolean deletePet(Integer petId) {
        Optional<Pets> petOptional = petsRepository.findById(petId);

        if (petOptional.isPresent()) {
            Pets pet = petOptional.get();

            // Eliminar todas las reservas asociadas a esta mascota antes de eliminarla
            reservationsRepository.deleteByPet(pet);

            // Ahora eliminamos la mascota
            petsRepository.delete(pet);

            // Eliminar imagen si existe
            if (pet.getImage() != null) {
                try {
                    imagesS3Bl.deleteFile(pet.getImage().getImageId());
                } catch (Exception e) {
                    throw new RuntimeException("Error al eliminar la imagen asociada", e);
                }
            }

            return true;
        }

        return false;
    }

    // Obtener todas las mascotas por ID de usuario
    public List<PetsDto> getPetsByUserId(Integer userId) {
        List<Pets> pets = petsRepository.findByUserId(userId);
        return pets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Convertir entidad a DTO
    private PetsDto convertToDto(Pets pet) {
        String imageUrl = null;
        if (pet.getImage() != null) {
            // Generar el enlace de la imagen desde MinIO
            imageUrl = imagesS3Bl.generateFileUrl(pet.getImage().getFileName());
        }

        return new PetsDto(
                pet.getPetId(),
                pet.getPetName(),
                pet.getPetBreed(),
                pet.getPetAge(),
                pet.getCreatedAt(),
                pet.getWeight(),
                pet.getHeight(),
                pet.getGender(),
                pet.getAllergies(),
                pet.getBehaviorNotes(),
                pet.getUser().getUserId(),
                pet.getImage() != null ? pet.getImage().getImageId() : null,
                imageUrl,
                pet.getBirthDate(),
                pet.getSpecies()
        );

    }
}
