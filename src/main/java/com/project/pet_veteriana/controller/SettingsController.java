package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.SettingsBl;
import com.project.pet_veteriana.config.JwtTokenProvider;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.dto.SettingsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private SettingsBl settingsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseDto<SettingsDto> createSetting(HttpServletRequest request, @RequestBody SettingsDto settingsDto) {
        String token = request.getHeader("Authorization");
        logger.info("Received request to create setting with token: {}", token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        SettingsDto createdSetting = settingsBl.createSetting(settingsDto);
        logger.info("Setting created successfully: {}", createdSetting);
        return ResponseDto.success(createdSetting, "Setting created successfully");
    }

    @GetMapping
    public ResponseDto<List<SettingsDto>> getAllSettings(@RequestHeader("Authorization") String token) {
        logger.info("Received request to fetch all settings with token: {}", token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        List<SettingsDto> settings = settingsBl.getAllSettings();
        logger.info("Fetched all settings: {}", settings);
        return ResponseDto.success(settings, "Settings fetched successfully");
    }

    @GetMapping("/{id}")
    public ResponseDto<SettingsDto> getSettingById(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Received request to fetch setting with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        SettingsDto settingsDto = settingsBl.getSettingById(id);
        logger.info("Fetched setting by ID: {} - {}", id, settingsDto);
        return ResponseDto.success(settingsDto, "Setting found");
    }

    @PutMapping("/{id}")
    public ResponseDto<SettingsDto> updateSetting(@PathVariable("id") Integer id, @RequestBody SettingsDto settingsDto, @RequestHeader("Authorization") String token) {
        logger.info("Received request to update setting with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        SettingsDto updatedSetting = settingsBl.updateSetting(id, settingsDto);
        logger.info("Updated setting with ID: {} - {}", id, updatedSetting);
        return ResponseDto.success(updatedSetting, "Setting updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteSetting(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Received request to delete setting with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401); // Retornar 401 Unauthorized si el token no es válido
        }

        settingsBl.deleteSetting(id);
        logger.info("Deleted setting with ID: {}", id);
        return ResponseDto.success("Deleted", "Setting deleted successfully");
    }
}
