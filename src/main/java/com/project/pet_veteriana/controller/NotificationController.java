package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.NotificationService;
import com.project.pet_veteriana.dto.SubscriptionRequest;
import com.project.pet_veteriana.dto.SupportNotificationRequest;
import com.project.pet_veteriana.dto.TransactionNotificationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Enviar notificación al nuevo vendedor
    @PostMapping("/subscribe-seller")
    public ResponseEntity<String> sendSellerSubscriptionNotification(@RequestBody SubscriptionRequest request) {
        try {
            notificationService.sendSubscriptionNotification(request.getEmail(), request.getSellerName());
            return ResponseEntity.ok("Notificación enviada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar la notificación: " + e.getMessage());
        }
    }

    // Enviar notificación de transacción al comprador
    @PostMapping("/transaction")
    public ResponseEntity<String> sendTransactionNotification(@RequestBody TransactionNotificationRequest request) {
        try {
            notificationService.sendTransactionNotification(request);
            return ResponseEntity.ok("Notificación de transacción enviada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar la notificación: " + e.getMessage());
        }
    }

    @PostMapping("/support")
    public ResponseEntity<String> sendSupportNotification(@RequestBody SupportNotificationRequest request) {
        try {
            notificationService.sendSupportNotification(request);
            return ResponseEntity.ok("Notificación de soporte enviada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar la notificación de soporte: " + e.getMessage());
        }
    }
}
