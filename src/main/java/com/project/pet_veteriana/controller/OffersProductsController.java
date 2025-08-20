package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.OffersProductsBl;
import com.project.pet_veteriana.dto.OffersProductsDto;
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
@RequestMapping("/api/offers-products")
public class OffersProductsController {

    private static final Logger logger = LoggerFactory.getLogger(OffersProductsController.class);

    @Autowired
    private OffersProductsBl offersProductsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una relación OffersProducts
    @PostMapping
    public ResponseEntity<ResponseDto<OffersProductsDto>> createOffersProducts(HttpServletRequest request, @RequestBody OffersProductsDto dto) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersProductsDto created = offersProductsBl.createOffersProducts(dto);
        return new ResponseEntity<>(ResponseDto.success(created, "OffersProducts created successfully"), HttpStatus.CREATED);
    }

    // Obtener todas las relaciones OffersProducts
    @GetMapping
    public ResponseEntity<ResponseDto<List<OffersProductsDto>>> getAllOffersProducts(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<OffersProductsDto> offersProducts = offersProductsBl.getAllOffersProducts();
        return new ResponseEntity<>(ResponseDto.success(offersProducts, "OffersProducts fetched successfully"), HttpStatus.OK);
    }

    // Obtener una relación OffersProducts por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersProductsDto>> getOffersProductsById(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersProductsDto offersProducts = offersProductsBl.getOffersProductsById(id);
        return new ResponseEntity<>(ResponseDto.success(offersProducts, "OffersProducts fetched successfully"), HttpStatus.OK);
    }

    // Actualizar una relación OffersProducts
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersProductsDto>> updateOffersProducts(@PathVariable Integer id, @RequestBody OffersProductsDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersProductsDto updated = offersProductsBl.updateOffersProducts(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updated, "OffersProducts updated successfully"), HttpStatus.OK);
    }

    // Eliminar una relación OffersProducts
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteOffersProducts(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(token);

        if (username == null || !jwtTokenProvider.validateToken(token, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean deleted = offersProductsBl.deleteOffersProducts(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "OffersProducts deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("OffersProducts not found", 404), HttpStatus.NOT_FOUND);
        }
    }
}
