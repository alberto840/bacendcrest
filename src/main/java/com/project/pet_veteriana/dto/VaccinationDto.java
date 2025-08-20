package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class VaccinationDto {

    private Integer vaccinationId;
    private String name;
    private LocalDateTime createdAt;

    public VaccinationDto() {
    }

    public VaccinationDto(Integer vaccinationId, String name, LocalDateTime createdAt) {
        this.vaccinationId = vaccinationId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Integer getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(Integer vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
