package com.project.pet_veteriana.entity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vaccination")
public class Vaccination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccination_id", nullable = false)
    private Integer vaccinationId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Vaccination() {
    }

    public Vaccination(Integer vaccinationId, String name, LocalDateTime createdAt) {
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