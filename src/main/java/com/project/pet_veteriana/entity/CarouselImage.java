package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "carousel_images")
public class CarouselImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String imageName;

    // Constructor vacío
    public CarouselImage() {
    }

    // Constructor
    public CarouselImage(String imageName) {
        this.imageName = imageName;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
