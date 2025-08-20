package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class UserPointsDto {

    private Integer userPointsId;
    private Double points;
    private String description;
    private LocalDateTime createdAt;
    private Integer userId;

    public UserPointsDto() {
    }

    public UserPointsDto(Integer userPointsId, Double points, String description, LocalDateTime createdAt, Integer userId) {
        this.userPointsId = userPointsId;
        this.points = points;
        this.description = description;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Integer getUserPointsId() {
        return userPointsId;
    }

    public void setUserPointsId(Integer userPointsId) {
        this.userPointsId = userPointsId;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
