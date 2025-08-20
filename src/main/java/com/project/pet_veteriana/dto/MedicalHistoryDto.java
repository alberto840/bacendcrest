package com.project.pet_veteriana.dto;


import java.time.LocalDateTime;

public class MedicalHistoryDto {

    private Integer medicalHistoryId;
    private LocalDateTime date;
    private String visitReason;
    private String symptoms;
    private LocalDateTime createdAt;
    private Integer petId;

    public MedicalHistoryDto() {
    }

    public MedicalHistoryDto(Integer medicalHistoryId, LocalDateTime date, String visitReason, String symptoms, LocalDateTime createdAt, Integer petId) {
        this.medicalHistoryId = medicalHistoryId;
        this.date = date;
        this.visitReason = visitReason;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
        this.petId = petId;
    }

    public Integer getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public void setMedicalHistoryId(Integer medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
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
}
