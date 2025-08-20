package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.UsersBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ChangePasswordDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.UsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersBl usersBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping
    public ResponseEntity<ResponseDto<UsersDto>> createUserWithImage(
            @RequestParam("user") String userDtoJson,
            @RequestParam("file") MultipartFile file) throws Exception {
        logger.info("Creating new user with image");

        try {
            // Convertir el JSON del usuario a UsersDto
            ObjectMapper objectMapper = new ObjectMapper();
            UsersDto usersDto = objectMapper.readValue(userDtoJson, UsersDto.class);

            // Crear usuario con imagen
            UsersDto createdUser = usersBl.createUserWithImage(usersDto, file);
            ResponseDto<UsersDto> response = ResponseDto.success(createdUser, "User created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            ResponseDto<UsersDto> response = ResponseDto.error("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<ResponseDto<List<UsersDto>>> getAllUsers() {
        logger.info("Fetching all users");
        List<UsersDto> users = usersBl.getAllUsers();
        ResponseDto<List<UsersDto>> response = ResponseDto.success(users, "Users fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UsersDto>> getUserById(@PathVariable("id") Integer id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<UsersDto> usersDto = usersBl.getUserById(id);
        if (usersDto.isPresent()) {
            ResponseDto<UsersDto> response = ResponseDto.success(usersDto.get(), "User found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("User with ID: {} not found", id);
            ResponseDto<UsersDto> response = ResponseDto.error("User not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UsersDto>> updateUserWithImage(
            @PathVariable("id") Integer id,
            @RequestParam("user") String userDtoJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        logger.info("Updating user with ID: {}", id);

        try {
            // Convertir el JSON del usuario a UsersDto
            ObjectMapper objectMapper = new ObjectMapper();
            UsersDto usersDto = objectMapper.readValue(userDtoJson, UsersDto.class);

            // Actualizar usuario con imagen
            Optional<UsersDto> updatedUser = usersBl.updateUserWithImage(id, usersDto, file);
            if (updatedUser.isPresent()) {
                ResponseDto<UsersDto> response = ResponseDto.success(updatedUser.get(), "User updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                logger.warn("User with ID: {} not found for update", id);
                ResponseDto<UsersDto> response = ResponseDto.error("User not found for update", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating user: {}", e.getMessage(), e);
            ResponseDto<UsersDto> response = ResponseDto.error("Error updating user", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable("id") Integer id) {
        logger.info("Deleting user with ID: {}", id);
        boolean deleted = usersBl.deleteUser(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "User deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            logger.warn("User with ID: {} not found for deletion", id);
            ResponseDto<Void> response = ResponseDto.error("User not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto<String>> changePassword(@RequestHeader("Authorization") String token,
                                                              @RequestBody ChangePasswordDto changePasswordDto) {
        logger.info("Attempting to change password for user with token: {}", token);
        try {
            // Extraer el email del token JWT
            String email = jwtTokenProvider.extractUsername(token.substring(7));
            // Registrar el intento de cambio de contraseña
            logger.info("Changing password for user with email: {}", email);
            // Cambiar la contraseña
            usersBl.changePassword(email, changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
            // Registrar éxito
            logger.info("Password successfully changed for user with email: {}", email);
            return new ResponseEntity<>(ResponseDto.success("Password changed successfully", "Password changed successfully"), HttpStatus.OK);
        } catch (Exception e) {
            // Registrar error
            logger.error("Failed to change password for user with email: {}. Error: {}", token, e.getMessage());
            return new ResponseEntity<>(ResponseDto.error("Failed to change password", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

}
