package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Providers")
public class Providers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", nullable = false)
    private Integer providerId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // Permitir eliminación en cascada
    private Users user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = true)
    private ImageS3 image;

    @Column(name = "rating", nullable = false, precision = 3)
    private Double rating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "reviews", nullable = true) // Nuevo campo
    private Integer reviews = 0;

    @Column(name = "city", nullable = true, length = 100)
    private String city;

    @Column(name = "country", nullable = true, length = 100)
    private String country;

    @Column(name = "verified", nullable = false)
    private Boolean verified = false;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;


    public Providers() {
    }

    public Providers(Integer providerId, Users user, String name, String description, String address, ImageS3 image, Double rating, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean status, Integer reviews, String city, String country, Boolean verified, String phone) {
        this.providerId = providerId;
        this.user = user;
        this.name = name;
        this.description = description;
        this.address = address;
        this.image = image;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.reviews = reviews;
        this.city = city;
        this.country = country;
        this.verified = verified;
        this.phone = phone;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
