package com.project.pet_veteriana.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "preferred_language", nullable = false, length = 10)
    private String preferredLanguage;

    @Column(name = "last_login", nullable = true)
    private LocalDateTime lastLogin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    // Relación con Rol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    // Relación con ImageS3
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImageS3_image_id", referencedColumnName = "image_id", nullable = true)
    private ImageS3 image;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    public Users() {
    }

    public Users(Integer userId, String name, String email, String password, String phoneNumber, String location, String preferredLanguage, LocalDateTime lastLogin, LocalDateTime createdAt, Boolean status, Rol rol, ImageS3 image) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.preferredLanguage = preferredLanguage;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.status = status;
        this.rol = rol;
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }
}
