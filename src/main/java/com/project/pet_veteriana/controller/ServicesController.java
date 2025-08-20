package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.ServicesBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.ServicesDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServicesController {

    private static final Logger logger = LoggerFactory.getLogger(ServicesController.class);

    @Autowired
    private ServicesBl servicesBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<ServicesDto>> createServiceWithImage(
            HttpServletRequest request,
            @RequestParam("service") String serviceDtoJson,
            @RequestParam("file") MultipartFile file) throws Exception {

        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ServicesDto servicesDto = objectMapper.readValue(serviceDtoJson, ServicesDto.class);

        ServicesDto createdService = servicesBl.createServiceWithImage(servicesDto, file);
        ResponseDto<ServicesDto> response = ResponseDto.success(createdService, "Service created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ServicesDto>>> getAllServices(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ServicesDto> services = servicesBl.getAllServices();
        ResponseDto<List<ServicesDto>> response = ResponseDto.success(services, "Services fetched successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ServicesDto>> getServiceById(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        Optional<ServicesDto> service = servicesBl.getServiceById(id);
        if (service.isPresent()) {
            ResponseDto<ServicesDto> response = ResponseDto.success(service.get(), "Service found");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<ServicesDto> response = ResponseDto.error("Service not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ServicesDto>> updateServiceWithImage(
            @PathVariable("id") Integer id,
            @RequestParam("service") String serviceDtoJson,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String token) throws Exception {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ServicesDto servicesDto = objectMapper.readValue(serviceDtoJson, ServicesDto.class);

        Optional<ServicesDto> updatedService = servicesBl.updateServiceWithImage(id, servicesDto, file);
        if (updatedService.isPresent()) {
            ResponseDto<ServicesDto> response = ResponseDto.success(updatedService.get(), "Service updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDto<ServicesDto> response = ResponseDto.error("Service not found for update", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteService(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        boolean deleted = servicesBl.deleteService(id);
        if (deleted) {
            ResponseDto<Void> response = ResponseDto.success(null, "Service deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseDto<Void> response = ResponseDto.error("Service not found for deletion", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Obtener servicios recientes
    @GetMapping("/recent")
    public ResponseEntity<ResponseDto<List<ServicesDto>>> getRecentServices(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ServicesDto> services = servicesBl.getRecentServices();
        return new ResponseEntity<>(ResponseDto.success(services, "Recent services fetched successfully"), HttpStatus.OK);
    }

    // Obtener servicios en oferta
    @GetMapping("/on-offer")
    public ResponseEntity<ResponseDto<List<ServicesDto>>> getServicesOnOffer(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ServicesDto> services = servicesBl.getServicesOnOffer();
        return new ResponseEntity<>(ResponseDto.success(services, "Services on offer fetched successfully"), HttpStatus.OK);
    }

    // Obtener servicios por proveedor
    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<ResponseDto<List<ServicesDto>>> getServicesByProvider(
            @PathVariable Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        List<ServicesDto> services = servicesBl.getServicesByProvider(providerId);
        return new ResponseEntity<>(ResponseDto.success(services, "Services by provider fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/by-tipo/{tipoAtencion}")
    public ResponseEntity<ResponseDto<List<ServicesDto>>> getServicesByTipoAtencion(@PathVariable String tipoAtencion) {
        List<ServicesDto> services = servicesBl.getServicesByTipoAtencion(tipoAtencion);
        return new ResponseEntity<>(ResponseDto.success(services, "Services filtered by tipoAtencion successfully"), HttpStatus.OK);
    }

}
