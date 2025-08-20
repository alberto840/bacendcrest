package com.project.pet_veteriana.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PetsDto {

    private Integer petId;
    private String petName;
    private String petBreed;
    private String petAge;
    private LocalDateTime createdAt;
    private Double weight;
    private Double height;
    private String gender;
    private String allergies;
    private String behaviorNotes;
    private Integer userId;
    private Integer imageId;
    private String imageUrl;
    private LocalDate birthDate;
    private String species;


    public PetsDto() {
    }

    public PetsDto(Integer petId, String petName, String petBreed, String petAge, LocalDateTime createdAt, Double weight, Double height, String gender, String allergies, String behaviorNotes, Integer userId, Integer imageId, String imageUrl, LocalDate birthDate, String species) {
        this.petId = petId;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.createdAt = createdAt;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.allergies = allergies;
        this.behaviorNotes = behaviorNotes;
        this.userId = userId;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.birthDate = birthDate;
        this.species = species;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getBehaviorNotes() {
        return behaviorNotes;
    }

    public void setBehaviorNotes(String behaviorNotes) {
        this.behaviorNotes = behaviorNotes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
