package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Specialty")

public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id", nullable = false)
    private Integer specialtyId;

    @Column(name = "name_specialty", nullable = false, length = 50)
    private String nameSpecialty;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con SprecialityProviders
    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SprecialityProviders> specialtyProviders;

    public Specialty() {
    }

    public Specialty(Integer specialtyId, String nameSpecialty, LocalDateTime createdAt, List<SprecialityProviders> specialtyProviders) {
        this.specialtyId = specialtyId;
        this.nameSpecialty = nameSpecialty;
        this.createdAt = createdAt;
        this.specialtyProviders = specialtyProviders;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getNameSpecialty() {
        return nameSpecialty;
    }

    public void setNameSpecialty(String nameSpecialty) {
        this.nameSpecialty = nameSpecialty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<SprecialityProviders> getSpecialtyProviders() {
        return specialtyProviders;
    }

    public void setSpecialtyProviders(List<SprecialityProviders> specialtyProviders) {
        this.specialtyProviders = specialtyProviders;
    }
}