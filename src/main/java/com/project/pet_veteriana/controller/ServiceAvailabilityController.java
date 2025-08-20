package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.ServiceAvailabilityBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.ServiceAvailabilityDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/service-availability")
public class ServiceAvailabilityController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceAvailabilityController.class);

    @Autowired
    private ServiceAvailabilityBl serviceAvailabilityBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{serviceId}")
    public ResponseEntity<ResponseDto<List<ServiceAvailabilityDto>>> getAvailableHoursByService(
            @PathVariable Integer serviceId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching available hours for service ID: {}", serviceId);
        List<ServiceAvailabilityDto> availableHours = serviceAvailabilityBl.getAvailableHoursByService(serviceId);
        return ResponseEntity.ok(ResponseDto.success(availableHours, "Available hours retrieved successfully"));
    }

    @PostMapping("/{serviceId}")
    public ResponseEntity<ResponseDto<List<ServiceAvailabilityDto>>> addAvailableHoursBulk(
            @PathVariable Integer serviceId,
            @RequestBody String requestBody,
            @RequestHeader("Authorization") String token) throws Exception {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Adding available hours for service ID: {}", serviceId);

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> availableHoursList = objectMapper.readValue(requestBody, List.class);

        List<LocalTime> localTimes = availableHoursList.stream()
                .map(LocalTime::parse)
                .toList();

        List<ServiceAvailabilityDto> addedHours = serviceAvailabilityBl.addAvailableHoursBulk(serviceId, localTimes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(addedHours, "Available hours added successfully"));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<ResponseDto<Void>> deleteAvailability(
            @PathVariable Integer availabilityId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt.");
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting availability with ID: {}", availabilityId);
        boolean deleted = serviceAvailabilityBl.deleteAvailability(availabilityId);
        if (deleted) {
            return ResponseEntity.ok(ResponseDto.success(null, "Availability deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("Availability not found", HttpStatus.NOT_FOUND.value()));
    }

    @PostMapping("/{availabilityId}/reserve")
    public ResponseEntity<ResponseDto<ServiceAvailabilityDto>> markAsReserved(
            @PathVariable Integer availabilityId) {

        ServiceAvailabilityDto reservedAvailability = serviceAvailabilityBl.markAsReserved(availabilityId);
        return ResponseEntity.ok(ResponseDto.success(reservedAvailability, "Availability marked as reserved"));
    }
}
