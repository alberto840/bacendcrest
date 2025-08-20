package com.project.pet_veteriana.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Integer notificationId;

    @Column(name = "message", nullable = false, length = 250)
    private String message;

    @Column(name = "notification_type", nullable = false, length = 50)
    private String notificationType;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users user;

    public Notifications() {
    }

    public Notifications(Integer notificationId, String message, String notificationType, Boolean isRead, LocalDateTime createdAt, Users user) {
        this.notificationId = notificationId;
        this.message = message;
        this.notificationType = notificationType;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.user = user;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
