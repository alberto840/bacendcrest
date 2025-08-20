package com.project.pet_veteriana.dto;
import java.time.LocalDateTime;

public class NotificationsDto {

    private Integer notificationId;
    private String message;
    private String notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Integer userId;

    public NotificationsDto() {
    }

    public NotificationsDto(Integer notificationId, String message, String notificationType, Boolean isRead, LocalDateTime createdAt, Integer userId) {
        this.notificationId = notificationId;
        this.message = message;
        this.notificationType = notificationType;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
