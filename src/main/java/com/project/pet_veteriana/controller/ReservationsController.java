package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.ReservationsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ReservationsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationsController.class);

    @Autowired
    private ReservationsBl reservationsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<ReservationsDto>> createReservation(HttpServletRequest request, @RequestBody ReservationsDto dto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Creating new reservation by user: {}", username);
        ReservationsDto createdReservation = reservationsBl.createReservation(dto);
        return new ResponseEntity<>(ResponseDto.success(createdReservation, "Reservation created successfully"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ReservationsDto>>> getAllReservations(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching all reservations by user: {}", username);
        List<ReservationsDto> reservations = reservationsBl.getAllReservations();
        return new ResponseEntity<>(ResponseDto.success(reservations, "Reservations fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ReservationsDto>> getReservationById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching reservation with ID: {} by user: {}", id, username);
        ReservationsDto reservation = reservationsBl.getReservationById(id);
        return new ResponseEntity<>(ResponseDto.success(reservation, "Reservation fetched successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ReservationsDto>> updateReservation(@PathVariable Integer id, @RequestBody ReservationsDto dto, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating reservation with ID: {} by user: {}", id, username);
        ReservationsDto updatedReservation = reservationsBl.updateReservation(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updatedReservation, "Reservation updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<List<ReservationsDto>>> getReservationsByUser(
            @PathVariable Integer userId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching reservations for user with ID: {} by user: {}", userId, username);
        List<ReservationsDto> reservations = reservationsBl.getByIdUser(userId);
        return new ResponseEntity<>(ResponseDto.success(reservations, "Reservations fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ResponseDto<List<ReservationsDto>>> getReservationsByProvider(
            @PathVariable Integer providerId,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Fetching reservations for provider with ID: {} by user: {}", providerId, username);
        List<ReservationsDto> reservations = reservationsBl.getByIdProvider(providerId);
        return new ResponseEntity<>(ResponseDto.success(reservations, "Reservations fetched successfully"), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteReservation(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Deleting reservation with ID: {} by user: {}", id, username);
        boolean deleted = reservationsBl.deleteReservation(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Reservation deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Reservation not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDto<ReservationsDto>> updateReservationStatus(
            @PathVariable Integer id,
            @RequestParam String status,
            @RequestHeader("Authorization") String token) {

        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("Updating reservation status with ID: {} by user: {}", id, username);
        ReservationsDto updatedReservation = reservationsBl.updateReservationStatus(id, status);
        return new ResponseEntity<>(ResponseDto.success(updatedReservation, "Reservation status updated successfully"), HttpStatus.OK);
    }

}
