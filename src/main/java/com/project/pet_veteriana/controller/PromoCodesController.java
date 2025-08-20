package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.PromoCodesBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.PromoCodesDto;
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
@RequestMapping("/api/promo-codes")
public class PromoCodesController {

    private static final Logger logger = LoggerFactory.getLogger(PromoCodesController.class);

    @Autowired
    private PromoCodesBl promoCodesBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo código promocional
    @PostMapping
    public ResponseEntity<ResponseDto<PromoCodesDto>> createPromoCode(HttpServletRequest request, @RequestBody PromoCodesDto promoCodesDto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        PromoCodesDto createdPromoCode = promoCodesBl.createPromoCode(promoCodesDto);
        ResponseDto<PromoCodesDto> response = ResponseDto.success(createdPromoCode, "Código promocional creado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos los códigos promocionales
    @GetMapping
    public ResponseEntity<ResponseDto<List<PromoCodesDto>>> getAllPromoCodes(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<PromoCodesDto> promoCodes = promoCodesBl.getAllPromoCodes();
        ResponseDto<List<PromoCodesDto>> response = ResponseDto.success(promoCodes, "Códigos promocionales obtenidos exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener código promocional por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PromoCodesDto>> getPromoCodeById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        PromoCodesDto promoCode = promoCodesBl.getPromoCodeById(id);
        ResponseDto<PromoCodesDto> response = ResponseDto.success(promoCode, "Código promocional obtenido exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Actualizar código promocional
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PromoCodesDto>> updatePromoCode(@PathVariable Integer id, @RequestBody PromoCodesDto promoCodesDto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        PromoCodesDto updatedPromoCode = promoCodesBl.updatePromoCode(id, promoCodesDto);
        ResponseDto<PromoCodesDto> response = ResponseDto.success(updatedPromoCode, "Código promocional actualizado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar código promocional
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deletePromoCode(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean isDeleted = promoCodesBl.deletePromoCode(id);
        if (isDeleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Código promocional eliminado exitosamente");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Código promocional no encontrado", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener el número de usos actuales de un código promocional
    @GetMapping("/{id}/uses")
    public ResponseEntity<ResponseDto<Integer>> getCurrentUses(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        Integer currentUses = promoCodesBl.getCurrentUses(id);
        return new ResponseEntity<>(ResponseDto.success(currentUses, "Usos actuales del código obtenidos correctamente"), HttpStatus.OK);
    }

    // Incrementar el contador de usos del código promocional
    @PutMapping("/{id}/use")
    public ResponseEntity<ResponseDto<PromoCodesDto>> usePromoCode(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Acceso no autorizado con el token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        PromoCodesDto updatedPromoCode = promoCodesBl.incrementPromoCodeUsage(id);
        return new ResponseEntity<>(ResponseDto.success(updatedPromoCode, "Uso del código registrado correctamente"), HttpStatus.OK);
    }

}
