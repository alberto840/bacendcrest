package com.project.pet_veteriana.entity;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Medical_history")

public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id", nullable = false)
    private Integer medicalHistoryId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "visit_reason", nullable = false, length = 150)
    private String visitReason;

    @Column(name = "symptoms", nullable = false, length = 150)
    private String symptoms;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Pets
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pets pet;

    public MedicalHistory() {
    }

    public MedicalHistory(Integer medicalHistoryId, LocalDateTime date, String visitReason, String symptoms, LocalDateTime createdAt, Pets pet) {
        this.medicalHistoryId = medicalHistoryId;
        this.date = date;
        this.visitReason = visitReason;
        this.symptoms = symptoms;
        this.createdAt = createdAt;
        this.pet = pet;
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

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }
}
