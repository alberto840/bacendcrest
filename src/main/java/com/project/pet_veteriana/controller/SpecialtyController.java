package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SpecialtyBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.SpecialtyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    private static final Logger logger = LoggerFactory.getLogger(SpecialtyController.class);

    @Autowired
    private SpecialtyBl specialtyBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva especialidad
    @PostMapping
    public ResponseEntity<ResponseDto<SpecialtyDto>> createSpecialty(HttpServletRequest request, @RequestBody SpecialtyDto specialtyDto) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para crear especialidad: {}", specialtyDto.getNameSpecialty());

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SpecialtyDto createdSpecialty = specialtyBl.createSpecialty(specialtyDto);
        if (createdSpecialty != null) {
            return new ResponseEntity<>(ResponseDto.success(createdSpecialty, "Especialidad creada exitosamente"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Error al crear la especialidad", 400), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todas las especialidades
    @GetMapping
    public ResponseEntity<ResponseDto<List<SpecialtyDto>>> getAllSpecialties(@RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener todas las especialidades");

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<SpecialtyDto> specialties = specialtyBl.getAllSpecialties();
        if (!specialties.isEmpty()) {
            return new ResponseEntity<>(ResponseDto.success(specialties, "Especialidades obtenidas exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("No se encontraron especialidades", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener especialidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<SpecialtyDto>> getSpecialtyById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener especialidad con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SpecialtyDto specialty = specialtyBl.getSpecialtyById(id);
        if (specialty != null) {
            return new ResponseEntity<>(ResponseDto.success(specialty, "Especialidad obtenida exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Especialidad no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar especialidad
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<SpecialtyDto>> updateSpecialty(@PathVariable Integer id, @RequestBody SpecialtyDto specialtyDto, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para actualizar especialidad con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SpecialtyDto updatedSpecialty = specialtyBl.updateSpecialty(id, specialtyDto);
        if (updatedSpecialty != null) {
            return new ResponseEntity<>(ResponseDto.success(updatedSpecialty, "Especialidad actualizada exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Especialidad no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar especialidad
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteSpecialty(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para eliminar especialidad con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = specialtyBl.deleteSpecialty(id);
        if (isDeleted) {
            return new ResponseEntity<>(ResponseDto.success(null,"Especialidad eliminada exitosamente"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Especialidad no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }
}
