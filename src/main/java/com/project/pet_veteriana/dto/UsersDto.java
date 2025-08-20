package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class UsersDto {

    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String location;
    private String preferredLanguage;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer rolId;
    private Integer imageId;
    private String imageUrl;
    private Integer providerId;

    // Constructor por defecto
    public UsersDto() {
    }

    // Constructor con parámetros
    public UsersDto(Integer userId, String name, String email, String password, String phoneNumber, String location,
                    String preferredLanguage, LocalDateTime lastLogin, LocalDateTime createdAt, Boolean status,
                    Integer rolId, Integer imageId, String imageUrl, Integer providerId) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = (password != null) ? password : "";
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.preferredLanguage = preferredLanguage;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.status = status;
        this.rolId = rolId;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
    }

    // Getters y Setters
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
        return password; // Getter para la contraseña
    }

    public void setPassword(String password) {
        this.password = password; // Setter para la contraseña
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

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
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

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
