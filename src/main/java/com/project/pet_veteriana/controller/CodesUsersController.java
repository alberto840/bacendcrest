package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.CodesUsersService;
import com.project.pet_veteriana.dto.CodesUsersDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/codes-users")
public class CodesUsersController {

    private static final Logger logger = LoggerFactory.getLogger(CodesUsersController.class);

    @Autowired
    private CodesUsersService codesUsersService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Obtener todos los códigos de usuarios
    @GetMapping
    public ResponseDto<List<CodesUsersDto>> getAllCodesUsers(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} obteniendo todos los códigos de usuarios.", username);
        List<CodesUsersDto> codesUsers = codesUsersService.getAllCodesUsers();
        return ResponseDto.success(codesUsers, "Codes Users fetched successfully");
    }

    // Obtener un código de usuario por ID
    @GetMapping("/{id}")
    public ResponseDto<CodesUsersDto> getCodesUsersById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} buscando código de usuario con ID: {}", username, id);
        CodesUsersDto codesUsers = codesUsersService.getCodesUsersById(id);
        if (codesUsers == null) {
            logger.warn("Código de usuario con ID {} no encontrado.", id);
            return ResponseDto.error("Codes Users not found", HttpStatus.NOT_FOUND.value());
        }
        return ResponseDto.success(codesUsers, "Codes Users fetched successfully");
    }

    // Obtener todos los códigos por ID de usuario (cupones adquiridos)
    @GetMapping("/user/{userId}")
    public ResponseDto<List<CodesUsersDto>> getAllByUserId(@PathVariable Integer userId, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} obteniendo cupones adquiridos por el usuario con ID: {}", username, userId);
        List<CodesUsersDto> codesUsers = codesUsersService.getAllByUserId(userId);
        return ResponseDto.success(codesUsers, "User's Codes Users fetched successfully");
    }

    // Obtener todos los códigos por ID de proveedor (cupones de un proveedor)
    @GetMapping("/provider/{providerId}")
    public ResponseDto<List<CodesUsersDto>> getAllByProviderId(@PathVariable Integer providerId, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} obteniendo cupones asociados al proveedor con ID: {}", username, providerId);
        List<CodesUsersDto> codesUsers = codesUsersService.getAllByProviderId(providerId);
        return ResponseDto.success(codesUsers, "Provider's Codes Users fetched successfully");
    }

    // Crear un nuevo código de usuario
    @PostMapping
    public ResponseDto<CodesUsersDto> createCodesUsers(@RequestBody CodesUsersDto codesUsersDto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} creando un nuevo código de usuario con datos: {}", username, codesUsersDto);
        try {
            CodesUsersDto createdCodesUsers = codesUsersService.createCodesUsers(codesUsersDto);
            return ResponseDto.success(createdCodesUsers, "Codes Users created successfully");
        } catch (Exception e) {
            logger.error("Error al crear código de usuario: {}", e.getMessage());
            return ResponseDto.error("Error creating Codes Users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Actualizar un código de usuario
    @PutMapping("/{id}")
    public ResponseDto<CodesUsersDto> updateCodesUsers(@PathVariable Integer id, @RequestBody CodesUsersDto codesUsersDto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} actualizando código de usuario con ID: {}", username, id);
        try {
            CodesUsersDto updatedCodesUsers = codesUsersService.updateCodesUsers(id, codesUsersDto);
            return ResponseDto.success(updatedCodesUsers, "Codes Users updated successfully");
        } catch (Exception e) {
            logger.error("Error al actualizar código de usuario con ID {}: {}", id, e.getMessage());
            return ResponseDto.error("Error updating Codes Users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Eliminar un código de usuario
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteCodesUsers(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        logger.info("Usuario {} eliminando código de usuario con ID: {}", username, id);
        try {
            codesUsersService.deleteCodesUsers(id);
            return ResponseDto.success("Deleted", "Codes Users deleted successfully");
        } catch (Exception e) {
            logger.error("Error al eliminar código de usuario con ID {}: {}", id, e.getMessage());
            return ResponseDto.error("Error deleting Codes Users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
