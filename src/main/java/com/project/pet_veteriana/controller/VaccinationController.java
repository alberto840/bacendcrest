package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.VaccinationBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.VaccinationDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private static final Logger logger = LoggerFactory.getLogger(VaccinationController.class);

    @Autowired
    private VaccinationBl vaccinationBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva vacunación
    @PostMapping
    public ResponseEntity<ResponseDto<VaccinationDto>> createVaccination(HttpServletRequest request, @RequestBody VaccinationDto vaccinationDto) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para crear vacunación: {}", vaccinationDto.getName());

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        VaccinationDto createdVaccination = vaccinationBl.createVaccination(vaccinationDto);
        if (createdVaccination != null) {
            return new ResponseEntity<>(ResponseDto.success(createdVaccination, "Vacunación creada exitosamente"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Error al crear la vacunación", 400), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener todas las vacunaciones
    @GetMapping
    public ResponseEntity<ResponseDto<List<VaccinationDto>>> getAllVaccinations(@RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener todas las vacunaciones");

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<VaccinationDto> vaccinations = vaccinationBl.getAllVaccinations();
        if (!vaccinations.isEmpty()) {
            return new ResponseEntity<>(ResponseDto.success(vaccinations, "Vacunaciones obtenidas exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("No se encontraron vacunaciones", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener vacunación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<VaccinationDto>> getVaccinationById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para obtener vacunación con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        VaccinationDto vaccination = vaccinationBl.getVaccinationById(id);
        if (vaccination != null) {
            return new ResponseEntity<>(ResponseDto.success(vaccination, "Vacunación obtenida exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Vacunación no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar vacunación
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<VaccinationDto>> updateVaccination(@PathVariable Integer id, @RequestBody VaccinationDto vaccinationDto, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para actualizar vacunación con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        VaccinationDto updatedVaccination = vaccinationBl.updateVaccination(id, vaccinationDto);
        if (updatedVaccination != null) {
            return new ResponseEntity<>(ResponseDto.success(updatedVaccination, "Vacunación actualizada exitosamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Vacunación no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar vacunación
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteVaccination(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Solicitud para eliminar vacunación con ID: {}", id);

        // Validar el token JWT
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = vaccinationBl.deleteVaccination(id);
        if (isDeleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Vacunación eliminada exitosamente"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Vacunación no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }
}
