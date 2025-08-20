package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.OffersBl;
import com.project.pet_veteriana.dto.OffersDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    private static final Logger logger = LoggerFactory.getLogger(OffersController.class);

    @Autowired
    private OffersBl offersBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva oferta
    @PostMapping
    public ResponseEntity<ResponseDto<OffersDto>> createOffer(HttpServletRequest request, @RequestBody OffersDto offersDto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersDto createdOffer = offersBl.createOffer(offersDto);
        ResponseDto<OffersDto> response = ResponseDto.success(createdOffer, "Oferta creada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todas las ofertas
    @GetMapping
    public ResponseEntity<ResponseDto<List<OffersDto>>> getAllOffers(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<OffersDto> offers = offersBl.getAllOffers();
        ResponseDto<List<OffersDto>> response = ResponseDto.success(offers, "Ofertas obtenidas exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener una oferta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersDto>> getOfferById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersDto offer = offersBl.getOfferById(id);
        ResponseDto<OffersDto> response = ResponseDto.success(offer, "Oferta obtenida exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Actualizar una oferta
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<OffersDto>> updateOffer(@PathVariable Integer id, @RequestBody OffersDto offersDto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        OffersDto updatedOffer = offersBl.updateOffer(id, offersDto);
        ResponseDto<OffersDto> response = ResponseDto.success(updatedOffer, "Oferta actualizada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar una oferta
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteOffer(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = offersBl.deleteOffer(id);
        if (isDeleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Oferta eliminada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Oferta no encontrada", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
