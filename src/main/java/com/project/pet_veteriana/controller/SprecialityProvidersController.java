package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SpecialityProvidersBl;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.SprecialityProvidersDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speciality-providers")
public class SprecialityProvidersController {

    @Autowired
    private SpecialityProvidersBl specialityProvidersBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva relación especialidad-proveedor
    @PostMapping
    public ResponseEntity<ResponseDto<SprecialityProvidersDto>> createSpecialityProvider(HttpServletRequest request, @RequestBody SprecialityProvidersDto dto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SprecialityProvidersDto created = specialityProvidersBl.createSpecialityProvider(dto);
        return new ResponseEntity<>(ResponseDto.success(created, "Relación creada exitosamente"), HttpStatus.CREATED);
    }

    // Obtener todas las relaciones especialidad-proveedor
    @GetMapping
    public ResponseEntity<ResponseDto<List<SprecialityProvidersDto>>> getAllSpecialityProviders(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<SprecialityProvidersDto> list = specialityProvidersBl.getAllSpecialityProviders();
        return new ResponseEntity<>(ResponseDto.success(list, "Relaciones obtenidas exitosamente"), HttpStatus.OK);
    }

    // Obtener una relación especialidad-proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<SprecialityProvidersDto>> getSpecialityProviderById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SprecialityProvidersDto dto = specialityProvidersBl.getSpecialityProviderById(id);
        return new ResponseEntity<>(ResponseDto.success(dto, "Relación obtenida exitosamente"), HttpStatus.OK);
    }

    // Actualizar una relación especialidad-proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<SprecialityProvidersDto>> updateSpecialityProvider(@PathVariable Integer id, @RequestBody SprecialityProvidersDto dto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        SprecialityProvidersDto updated = specialityProvidersBl.updateSpecialityProvider(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updated, "Relación actualizada exitosamente"), HttpStatus.OK);
    }

    // Eliminar una relación especialidad-proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteSpecialityProvider(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean deleted = specialityProvidersBl.deleteSpecialityProvider(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Relación eliminada exitosamente"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Relación no encontrada", 404), HttpStatus.NOT_FOUND);
        }
    }
}
