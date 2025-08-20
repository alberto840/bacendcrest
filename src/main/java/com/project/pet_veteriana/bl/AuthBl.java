package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.LoginRequestDto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthBl {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String login(LoginRequestDto loginRequest) {
        // Buscar usuario en la base de datos
        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verificar si la contraseña es correcta
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Crear un objeto UsersDto con los datos del usuario
        UsersDto userDto = new UsersDto();
        userDto.setUserId(user.getUserId());
        userDto.setRolId(user.getRol().getRolId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPreferredLanguage(user.getPreferredLanguage());

        // Verificar si el usuario es un vendedor
        Optional<Integer> providerId = providersRepository.findByUser_UserId(user.getUserId())
                .map(provider -> provider.getProviderId());

        // Si el usuario es un vendedor, agregar `providerId` en el token
        providerId.ifPresent(userDto::setProviderId);

        // Generar el token con todos los datos
        return jwtTokenProvider.generateToken(userDto);
    }
}
