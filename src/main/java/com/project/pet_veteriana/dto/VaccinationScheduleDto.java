package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class VaccinationScheduleDto {

    private Integer vaccinationScheduleId;
    private LocalDateTime dateVaccination;
    private String status;
    private LocalDateTime createdAt;
    private Integer petId;
    private Integer vaccinationId;

    public VaccinationScheduleDto() {
    }

    public VaccinationScheduleDto(Integer vaccinationScheduleId, LocalDateTime dateVaccination, String status, LocalDateTime createdAt, Integer petId, Integer vaccinationId) {
        this.vaccinationScheduleId = vaccinationScheduleId;
        this.dateVaccination = dateVaccination;
        this.status = status;
        this.createdAt = createdAt;
        this.petId = petId;
        this.vaccinationId = vaccinationId;
    }

    public Integer getVaccinationScheduleId() {
        return vaccinationScheduleId;
    }

    public void setVaccinationScheduleId(Integer vaccinationScheduleId) {
        this.vaccinationScheduleId = vaccinationScheduleId;
    }

    public LocalDateTime getDateVaccination() {
        return dateVaccination;
    }

    public void setDateVaccination(LocalDateTime dateVaccination) {
        this.dateVaccination = dateVaccination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(Integer vaccinationId) {
        this.vaccinationId = vaccinationId;
    }
}
