package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class CategoryDto {

    private Integer categoryId;
    private String nameCategory;
    private LocalDateTime createdAt;
    private String icono; // Nuevo atributo

    public CategoryDto() {
    }

    public CategoryDto(Integer categoryId, String nameCategory, LocalDateTime createdAt, String icono) {
        this.categoryId = categoryId;
        this.nameCategory = nameCategory;
        this.createdAt = createdAt;
        this.icono = icono;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
