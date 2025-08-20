package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.MassiveNotificationRequestDto;
import com.project.pet_veteriana.dto.NotificationsDto;
import com.project.pet_veteriana.entity.Notifications;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.repository.NotificationsRepository;
import com.project.pet_veteriana.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacionsBl {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UsersRepository userRepository;

    // Crear una nueva notificación
    @Transactional
    public NotificationsDto createNotification(NotificationsDto notificationsDto) {
        Notifications notification = new Notifications();
        notification.setMessage(notificationsDto.getMessage());
        notification.setNotificationType(notificationsDto.getNotificationType());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        // Obtener el usuario por ID y asignarlo a la notificación
        Optional<Users> user = userRepository.findById(notificationsDto.getUserId());
        if (user.isPresent()) {
            notification.setUser(user.get()); // Asignar el usuario encontrado
        } else {
            throw new RuntimeException("User not found with ID: " + notificationsDto.getUserId());
        }

        Notifications savedNotification = notificationsRepository.save(notification);

        return new NotificationsDto(
                savedNotification.getNotificationId(),
                savedNotification.getMessage(),
                savedNotification.getNotificationType(),
                savedNotification.getRead(),
                savedNotification.getCreatedAt(),
                savedNotification.getUser().getUserId() // Devuelve el userId de la notificación guardada
        );
    }

    // Obtener todas las notificaciones
    public List<NotificationsDto> getAllNotifications() {
        List<Notifications> notifications = notificationsRepository.findAll();
        return notifications.stream()
                .map(notification -> new NotificationsDto(
                        notification.getNotificationId(),
                        notification.getMessage(),
                        notification.getNotificationType(),
                        notification.getRead(),
                        notification.getCreatedAt(),
                        notification.getUser().getUserId()
                ))
                .collect(Collectors.toList());
    }

    // Obtener una notificación por ID
    public NotificationsDto getNotificationById(Integer id) {
        Optional<Notifications> notification = notificationsRepository.findById(id);
        return notification.map(not -> new NotificationsDto(
                not.getNotificationId(),
                not.getMessage(),
                not.getNotificationType(),
                not.getRead(),
                not.getCreatedAt(),
                not.getUser().getUserId()
        )).orElse(null);
    }

    // Actualizar una notificación
    @Transactional
    public NotificationsDto updateNotification(Integer id, NotificationsDto notificationsDto) {
        Optional<Notifications> optionalNotification = notificationsRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notifications notification = optionalNotification.get();
            notification.setMessage(notificationsDto.getMessage());
            notification.setNotificationType(notificationsDto.getNotificationType());

            // Asegurarse de que 'isRead' no sea null. Asignar un valor predeterminado si es null
            Boolean isRead = notificationsDto.getRead() != null ? notificationsDto.getRead() : false;
            notification.setRead(isRead);

            // No sobrescribir createdAt para preservar la fecha original
            notification.setCreatedAt(LocalDateTime.now());

            Notifications updatedNotification = notificationsRepository.save(notification);
            return new NotificationsDto(
                    updatedNotification.getNotificationId(),
                    updatedNotification.getMessage(),
                    updatedNotification.getNotificationType(),
                    updatedNotification.getRead(),
                    updatedNotification.getCreatedAt(),
                    updatedNotification.getUser().getUserId()
            );
        }
        return null; // Si no se encuentra la notificación
    }


    // Eliminar una notificación
    @Transactional
    public void deleteNotification(Integer id) {
        notificationsRepository.deleteById(id);
    }

    // Enviar notificaciones masivas
    @Transactional
    public void sendMassiveNotifications(MassiveNotificationRequestDto request) {
        List<Notifications> notifications = request.getUserIds().stream().map(userId -> {
            Optional<Users> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + userId);
            }

            Notifications notification = new Notifications();
            notification.setMessage(request.getMessage());
            notification.setNotificationType(request.getNotificationType());
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setUser(userOptional.get());
            return notification;
        }).collect(Collectors.toList());

        notificationsRepository.saveAll(notifications);
    }


}
