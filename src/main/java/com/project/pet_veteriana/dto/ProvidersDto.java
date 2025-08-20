package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ProvidersDto {

    private Integer providerId;
    private Integer userId;
    private String name;
    private String description;
    private String address;
    private String imageUrl;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean status;
    private Integer productCount;
    private Integer serviceCount;
    private Integer reviews;
    private String city;
    private String country;
    private Boolean verified; // ✅ Nuevo campo
    private String phone; // Nuevo campo para el número de teléfono

    public ProvidersDto() {
    }

    public ProvidersDto(Integer providerId, Integer userId, String name, String description, String address,
                        String imageUrl, Double rating, LocalDateTime createdAt, LocalDateTime updatedAt,
                        Boolean status, Integer productCount, Integer serviceCount, Integer reviews,
                        String city, String country, Boolean verified, String phone) {
        this.providerId = providerId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.productCount = productCount;
        this.serviceCount = serviceCount;
        this.reviews = reviews;
        this.city = city;
        this.country = country;
        this.verified = verified;
        this.phone = phone; // Asignación del número de teléfono
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
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
