package com.project.pet_veteriana.dto;
import java.time.LocalDateTime;

public class ActivityLogsDto {

    private Integer activityLogsId;
    private Integer userId;
    private String action;
    private String description;
    private String ip;
    private LocalDateTime createdAt;

    public ActivityLogsDto() {
    }

    public ActivityLogsDto(Integer activityLogsId, Integer userId, String action, String description, String ip, LocalDateTime createdAt) {
        this.activityLogsId = activityLogsId;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
