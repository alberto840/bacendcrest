package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.MedicalHistoryBl;
import com.project.pet_veteriana.dto.MedicalHistoryDto;
import com.project.pet_veteriana.dto.ResponseDto;
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
@RequestMapping("/api/medical-histories")
public class MedicalHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalHistoryController.class);

    @Autowired
    private MedicalHistoryBl medicalHistoryBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ResponseDto<List<MedicalHistoryDto>>> getAllMedicalHistories(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para obtener todos los historiales médicos");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<MedicalHistoryDto> histories = medicalHistoryBl.findAll();
        return new ResponseEntity<>(ResponseDto.success(histories, "Historiales médicos obtenidos exitosamente"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MedicalHistoryDto>> getMedicalHistoryById(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para obtener historial médico con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        MedicalHistoryDto history = medicalHistoryBl.findById(id);
        if (history != null) {
            return new ResponseEntity<>(ResponseDto.success(history, "Historial médico obtenido exitosamente"), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.error("Historial médico no encontrado", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<MedicalHistoryDto>> createMedicalHistory(HttpServletRequest request, @RequestBody MedicalHistoryDto dto) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para crear un nuevo historial médico");

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        MedicalHistoryDto created = medicalHistoryBl.create(dto);
        return new ResponseEntity<>(ResponseDto.success(created, "Historial médico creado exitosamente"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<MedicalHistoryDto>> updateMedicalHistory(@PathVariable Integer id, @RequestBody MedicalHistoryDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para actualizar historial médico con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        MedicalHistoryDto updated = medicalHistoryBl.update(id, dto);
        if (updated != null) {
            return new ResponseEntity<>(ResponseDto.success(updated, "Historial médico actualizado exitosamente"), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.error("Historial médico no encontrado", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMedicalHistory(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        logger.info("Solicitud para eliminar historial médico con ID: {}", id);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean deleted = medicalHistoryBl.delete(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Historial médico eliminado exitosamente"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ResponseDto.error("Historial médico no encontrado", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
