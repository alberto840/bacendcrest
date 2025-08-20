package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.NotificacionsBl;
import com.project.pet_veteriana.dto.MassiveNotificationRequestDto;
import com.project.pet_veteriana.dto.NotificationsDto;
import com.project.pet_veteriana.dto.ResponseDto;
import com.project.pet_veteriana.config.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsController.class);

    @Autowired
    private NotificacionsBl notificacionsBl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Crear una nueva notificación
    @PostMapping
    public ResponseDto<NotificationsDto> createNotification(HttpServletRequest request, @RequestBody NotificationsDto notificationsDto) {
        String token = request.getHeader("Authorization");
        logger.info("Received request to create notification with token: {}", token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        NotificationsDto createdNotification = notificacionsBl.createNotification(notificationsDto);
        logger.info("Notification created successfully: {}", createdNotification);
        return ResponseDto.success(createdNotification, "Notification created successfully");
    }

    // Obtener todas las notificaciones
    @GetMapping
    public ResponseDto<List<NotificationsDto>> getAllNotifications(@RequestHeader("Authorization") String token) {
        logger.info("Received request to fetch all notifications with token: {}", token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        List<NotificationsDto> notifications = notificacionsBl.getAllNotifications();
        logger.info("Fetched all notifications: {}", notifications);
        return ResponseDto.success(notifications, "Notifications fetched successfully");
    }

    // Obtener una notificación por ID
    @GetMapping("/{id}")
    public ResponseDto<NotificationsDto> getNotificationById(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Received request to fetch notification with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        NotificationsDto notificationDto = notificacionsBl.getNotificationById(id);
        if (notificationDto == null) {
            return ResponseDto.error("Notification not found", 404);
        }

        logger.info("Fetched notification by ID: {} - {}", id, notificationDto);
        return ResponseDto.success(notificationDto, "Notification found");
    }

    // Actualizar una notificación
    @PutMapping("/{id}")
    public ResponseDto<NotificationsDto> updateNotification(@PathVariable("id") Integer id, @RequestBody NotificationsDto notificationsDto, @RequestHeader("Authorization") String token) {
        logger.info("Received request to update notification with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        NotificationsDto updatedNotification = notificacionsBl.updateNotification(id, notificationsDto);
        if (updatedNotification == null) {
            return ResponseDto.error("Notification not found", 404);
        }

        logger.info("Updated notification with ID: {} - {}", id, updatedNotification);
        return ResponseDto.success(updatedNotification, "Notification updated successfully");
    }

    // Eliminar una notificación
    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteNotification(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("Received request to delete notification with ID: {} and token: {}", id, token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        notificacionsBl.deleteNotification(id);
        logger.info("Deleted notification with ID: {}", id);
        return ResponseDto.success("Deleted", "Notification deleted successfully");
    }

    // Enviar notificaciones masivas
    @PostMapping("/send-massive")
    public ResponseDto<String> sendMassiveNotifications(@RequestHeader("Authorization") String token,
                                                        @RequestBody MassiveNotificationRequestDto request) {
        logger.info("Received request to send massive notifications with token: {}", token);

        // Validar el token JWT antes de proceder
        String extractedToken = token.replace("Bearer ", "");
        String username = jwtTokenProvider.extractUsername(extractedToken);

        if (username == null || !jwtTokenProvider.validateToken(extractedToken, username)) {
            logger.error("Unauthorized access attempt with token: {}", token);
            return ResponseDto.error("Unauthorized", 401);
        }

        notificacionsBl.sendMassiveNotifications(request);
        logger.info("Massive notifications sent successfully to user IDs: {}", request.getUserIds());
        return ResponseDto.success("Notifications sent successfully", "Massive notifications sent successfully");
    }



}
