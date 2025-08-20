package com.project.pet_veteriana.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Activity_logs")

public class ActivityLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_logs_id", nullable = false)
    private Integer activityLogsId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users user;

    @Column(name = "action", nullable = false, length = 255)
    private String action;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "ip", nullable = false, length = 50)
    private String ip;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ActivityLogs() {
    }

    public ActivityLogs(Integer activityLogsId, Users user, String action, String description, String ip, LocalDateTime createdAt) {
        this.activityLogsId = activityLogsId;
        this.user = user;
        this.action = action;
        this.description = description;
        this.ip = ip;
        this.createdAt = createdAt;
    }

    public Integer getActivityLogsId() {
        return activityLogsId;
    }

    public void setActivityLogsId(Integer activityLogsId) {
        this.activityLogsId = activityLogsId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
