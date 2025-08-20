package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.bl.ProvidersBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProvidersController {

    @Autowired
    private ProvidersBl providersBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear un nuevo Provider con imagen
    @PostMapping
    public ResponseDto<ProvidersDto> createProvider(
            HttpServletRequest request,
            @RequestParam("provider") String providerJson,  // Se recibe como String
            @RequestPart("file") MultipartFile file  // La imagen sigue siendo un MultipartFile
    ) {
        String token = request.getHeader("Authorization");

        // Validar el token JWT antes de proceder
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            // Convertir JSON a DTO
            ObjectMapper objectMapper = new ObjectMapper();
            ProvidersDto providersDto = objectMapper.readValue(providerJson, ProvidersDto.class);

            // Crear el proveedor con imagen
            ProvidersDto createdProvider = providersBl.createProvider(extractedToken, providersDto, file);
            return ResponseDto.success(createdProvider, "Provider created successfully");
        } catch (Exception e) {
            return ResponseDto.error("Error creating provider: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // Obtener todos los Providers
    @GetMapping
    public ResponseDto<List<ProvidersDto>> getAllProviders(@RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        List<ProvidersDto> providers = providersBl.getAllProviders();
        return ResponseDto.success(providers, "Providers fetched successfully");
    }

    // Obtener Provider por ID
    @GetMapping("/{id}")
    public ResponseDto<ProvidersDto> getProviderById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Extraer y validar el token
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            // Llamar a la lógica de negocio pasando el token para validar si el usuario es dueño
            ProvidersDto provider = providersBl.getProviderById(id, extractedToken);
            return ResponseDto.success(provider, "Provider fetched successfully");
        } catch (Exception e) {
            return ResponseDto.error("Provider not found: " + e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }


    // Actualizar Provider con imagen
    @PutMapping("/{id}")
    public ResponseDto<ProvidersDto> updateProvider(
            @PathVariable Integer id,
            @RequestParam("provider") String providerJson,
            @RequestParam(value = "file", required = false) MultipartFile file,  // Imagen opcional
            @RequestHeader("Authorization") String token
    ) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            // Convertir JSON a DTO
            ObjectMapper objectMapper = new ObjectMapper();
            ProvidersDto providersDto = objectMapper.readValue(providerJson, ProvidersDto.class);

            // Llamar a la capa de lógica para actualizar
            ProvidersDto updatedProvider = providersBl.updateProvider(id, providersDto, file);
            return ResponseDto.success(updatedProvider, "Provider updated successfully");
        } catch (Exception e) {
            return ResponseDto.error("Error updating provider: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    // Eliminar Provider (borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteProvider(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            providersBl.deleteProvider(id);
            return ResponseDto.success("Deleted", "Provider deleted successfully");
        } catch (Exception e) {
            return ResponseDto.error("Error deleting provider: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @GetMapping("/exists/{userId}")
    public ResponseDto<Boolean> checkIfProviderExists(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token
    ) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        boolean exists = providersBl.existsByUserId(userId);
        return ResponseDto.success(exists, "Provider existence check successful");
    }

    // Obtener los proveedores con mejor rating
    @GetMapping("/top")
    public ResponseEntity<ResponseDto<List<ProvidersDto>>> getTopProviders(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ProvidersDto> providers = providersBl.getTopProviders();
        return new ResponseEntity<>(ResponseDto.success(providers, "Top providers fetched successfully"), HttpStatus.OK);
    }

    // Obtener los proveedores más recientes
    @GetMapping("/recent")
    public ResponseEntity<ResponseDto<List<ProvidersDto>>> getRecentProviders(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ProvidersDto> providers = providersBl.getRecentProviders();
        return new ResponseEntity<>(ResponseDto.success(providers, "Recent providers fetched successfully"), HttpStatus.OK);
    }

    // Obtener un proveedor por ID (tienda del proveedor)
    @GetMapping("/{id}/store")
    public ResponseEntity<ResponseDto<ProvidersDto>> getProviderStoreById(
            @PathVariable("id") Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        ProvidersDto provider = providersBl.getProviderStoreById(providerId);
        return new ResponseEntity<>(ResponseDto.success(provider, "Provider store fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/identify")
    public ResponseDto<ProvidersDto> identifyProvider(
            @RequestParam(required = false) Integer productId,
            @RequestParam(required = false) Integer serviceId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            return ResponseDto.error("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }

        try {
            ProvidersDto provider = providersBl.identifyProvider(productId, serviceId);
            return ResponseDto.success(provider, "Provider identified successfully");
        } catch (Exception e) {
            return ResponseDto.error("Error identifying provider: " + e.getMessage(), HttpStatus.NOT_FOUND.value());
        }
    }
}
