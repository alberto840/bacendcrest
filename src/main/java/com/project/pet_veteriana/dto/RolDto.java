package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class RolDto {

    private Integer rolId;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public RolDto() {
    }

    public RolDto(Integer rolId, String name, String description, LocalDateTime createdAt) {
        this.rolId = rolId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
