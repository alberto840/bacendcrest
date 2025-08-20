package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class SupportTicketsDto {

    private Integer supportTicketsId;
    private String subject;
    private String description;
    private String status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Integer userId;

    public SupportTicketsDto() {
    }

    public SupportTicketsDto(Integer supportTicketsId, String subject, String description, String status, LocalDateTime updatedAt, LocalDateTime createdAt, Integer userId) {
        this.supportTicketsId = supportTicketsId;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Integer getSupportTicketsId() {
        return supportTicketsId;
    }

    public void setSupportTicketsId(Integer supportTicketsId) {
        this.supportTicketsId = supportTicketsId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
