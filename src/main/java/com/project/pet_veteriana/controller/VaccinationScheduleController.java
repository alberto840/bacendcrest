package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.VaccinationScheduleBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.VaccinationScheduleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/vaccination-schedule")
public class VaccinationScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(VaccinationScheduleController.class);

    @Autowired
    private VaccinationScheduleBl vaccinationScheduleBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ResponseDto<VaccinationScheduleDto>> createVaccinationSchedule(
            HttpServletRequest request, @RequestBody VaccinationScheduleDto dto) {
        String token = request.getHeader("Authorization");
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("User [{}] is creating a new Vaccination Schedule", username);
        VaccinationScheduleDto createdSchedule = vaccinationScheduleBl.createVaccinationSchedule(dto);
        return new ResponseEntity<>(ResponseDto.success(createdSchedule, "Vaccination Schedule created successfully"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<VaccinationScheduleDto>>> getAllVaccinationSchedules(
            @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("User [{}] is fetching all Vaccination Schedules", username);
        List<VaccinationScheduleDto> schedules = vaccinationScheduleBl.getAllVaccinationSchedules();
        return new ResponseEntity<>(ResponseDto.success(schedules, "Vaccination Schedules fetched successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<VaccinationScheduleDto>> getVaccinationScheduleById(
            @PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("User [{}] is fetching Vaccination Schedule with ID: {}", username, id);
        VaccinationScheduleDto schedule = vaccinationScheduleBl.getVaccinationScheduleById(id);
        return new ResponseEntity<>(ResponseDto.success(schedule, "Vaccination Schedule fetched successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<VaccinationScheduleDto>> updateVaccinationSchedule(
            @PathVariable Integer id, @RequestBody VaccinationScheduleDto dto,
            @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("User [{}] is updating Vaccination Schedule with ID: {}", username, id);
        VaccinationScheduleDto updatedSchedule = vaccinationScheduleBl.updateVaccinationSchedule(id, dto);
        return new ResponseEntity<>(ResponseDto.success(updatedSchedule, "Vaccination Schedule updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteVaccinationSchedule(
            @PathVariable Integer id, @RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access with token: {}", token);
            return new ResponseEntity<>(ResponseDto.error("Unauthorized", 401), HttpStatus.UNAUTHORIZED);
        }

        logger.info("User [{}] is deleting Vaccination Schedule with ID: {}", username, id);
        boolean deleted = vaccinationScheduleBl.deleteVaccinationSchedule(id);
        if (deleted) {
            return new ResponseEntity<>(ResponseDto.success(null, "Vaccination Schedule deleted successfully"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ResponseDto.error("Vaccination Schedule not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
