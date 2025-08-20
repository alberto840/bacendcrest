package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "Pets")
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Integer petId;

    @Column(name = "pet_name", nullable = false, length = 150)
    private String petName;

    @Column(name = "pet_breed", nullable = false, length = 150)
    private String petBreed;

    @Column(name = "pet_age", nullable = false, length = 50)
    private String petAge;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "weight", nullable = false, precision = 10)
    private Double weight;  // Sin scale ni precision

    @Column(name = "height", nullable = false, precision = 10)
    private Double height;  // Sin scale ni precision

    @Column(name = "gender", length = 50, nullable = false)
    private String gender;

    @Column(name = "allergies", length = 250)
    private String allergies;

    @Column(name = "behavior_notes", length = 250)
    private String behaviorNotes;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "species", nullable = false, length = 100)
    private String species;

    // Relación con Users
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    // Relación con ImageS3
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = true)
    private ImageS3 image;

    public Pets() {
    }


    public Pets(Integer petId, String petName, String petBreed, String petAge, LocalDateTime createdAt, Double weight, Double height, String gender, String allergies, String behaviorNotes, LocalDate birthDate, String species, Users user, ImageS3 image) {
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
        this.birthDate = birthDate;
        this.species = species;
        this.user = user;
        this.image = image;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }
}
