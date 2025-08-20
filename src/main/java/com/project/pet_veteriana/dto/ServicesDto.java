package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ServicesDto {

    private Integer serviceId;
    private String serviceName;
    private Double price;
    private Integer duration;
    private String description;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer providerId;
    private ProvidersDto provider;
    private Integer imageId;
    private String imageUrl;
    private String tipoAtencion;
    private Integer subSubCategoriaId;
    private Boolean isOnSale;
    private Integer categoryId;

    public ServicesDto() {
    }

    public ServicesDto(Integer serviceId, String serviceName, Double price, Integer duration, String description, LocalDateTime createdAt, Boolean status, Integer providerId, ProvidersDto provider,Integer imageId, String imageUrl, String tipoAtencion, Integer subSubCategoriaId, Boolean isOnSale, Integer categoryId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.providerId = providerId;
        this.provider = provider;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.tipoAtencion = tipoAtencion;
        this.subSubCategoriaId = subSubCategoriaId;
        this.isOnSale = isOnSale;
        this.categoryId = categoryId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public ProvidersDto getProvider() {
        return provider;
    }

    public void setProvider(ProvidersDto provider) {
        this.provider = provider;
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

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public Integer getSubSubCategoriaId() {
        return subSubCategoriaId;
    }

    public void setSubSubCategoriaId(Integer subSubCategoriaId) {
        this.subSubCategoriaId = subSubCategoriaId;
    }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
