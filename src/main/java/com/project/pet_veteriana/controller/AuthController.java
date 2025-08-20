package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.AuthBl;
import com.project.pet_veteriana.bl.PasswordResetService;
import com.project.pet_veteriana.dto.LoginRequestDto;
import com.project.pet_veteriana.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthBl authBl;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequestDto loginRequest) {
        logger.info("Login attempt for email: {}", loginRequest.getEmail());

        try {
            // Llamar a la lógica de autenticación
            String token = authBl.login(loginRequest);

            logger.info("Login successful for email: {}", loginRequest.getEmail());
            return new ResponseEntity<>(ResponseDto.success(token, "Login successful"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Login failed for email: {}. Error: {}", loginRequest.getEmail(), e.getMessage());
            return new ResponseEntity<>(ResponseDto.error("Invalid credentials", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto<String>> forgotPassword(@RequestParam String email) {
        try {
            passwordResetService.resetPasswordAndSendEmail(email);
            return new ResponseEntity<>(ResponseDto.success("New password sent to your email", "Password reset successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }
}
