package com.project.pet_veteriana.dto;

import java.util.List;

public class MassiveNotificationRequestDto {
    private List<Integer> userIds;
    private String message;
    private String notificationType;

    // Getters y setters
    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
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
}
