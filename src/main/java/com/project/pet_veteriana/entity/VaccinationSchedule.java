package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vaccination_schedule")
public class VaccinationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_schedule_id", nullable = false)
    private Integer vaccinationScheduleId;

    @Column(name = "date_vaccination", nullable = false)
    private LocalDateTime dateVaccination;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Pets
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = true)
    private Pets pet;

    // Relación con Vaccination
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccination_id", nullable = false)
    private Vaccination vaccination;

    // Constructor por defecto
    public VaccinationSchedule() {
    }

    // Constructor con parámetros
    public VaccinationSchedule(Integer vaccinationScheduleId, LocalDateTime dateVaccination, String status, LocalDateTime createdAt, Pets pet, Vaccination vaccination) {
        this.vaccinationScheduleId = vaccinationScheduleId;
        this.dateVaccination = dateVaccination;
        this.status = status;
        this.createdAt = createdAt;
        this.pet = pet;
        this.vaccination = vaccination;
    }

    // Getter y Setter para vaccinationScheduleId
    public Integer getVaccinationScheduleId() {
        return vaccinationScheduleId;
    }

    public void setVaccinationScheduleId(Integer vaccinationScheduleId) {
        this.vaccinationScheduleId = vaccinationScheduleId;
    }

    // Getter y Setter para dateVaccination
    public LocalDateTime getDateVaccination() {
        return dateVaccination;
    }

    public void setDateVaccination(LocalDateTime dateVaccination) {
        this.dateVaccination = dateVaccination;
    }

    // Getter y Setter para status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter y Setter para createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter y Setter para pet
    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }

    // Getter y Setter para vaccination
    public Vaccination getVaccination() {
        return vaccination;
    }

    public void setVaccination(Vaccination vaccination) {
        this.vaccination = vaccination;
    }
}
