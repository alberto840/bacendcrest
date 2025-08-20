package com.project.pet_veteriana.dto;


import java.time.LocalDateTime;

public class SpecialtyDto {

    private Integer specialtyId;
    private String nameSpecialty;
    private LocalDateTime createdAt;

    public SpecialtyDto() {
    }

    public SpecialtyDto(Integer specialtyId, String nameSpecialty, LocalDateTime createdAt) {
        this.specialtyId = specialtyId;
        this.nameSpecialty = nameSpecialty;
        this.createdAt = createdAt;
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
}
