package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ProductsDto {

    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;
    private Boolean status;
    private Integer providerId;
    private ProvidersDto provider;
    private Integer categoryId;
    private Integer imageId;
    private String imageUrl;
    private Integer subSubCategoriaId;
    private Boolean isOnSale;


    public ProductsDto() {
    }

    public ProductsDto(Integer productId, String name, String description, Double price, Integer stock,
                       LocalDateTime createdAt, Boolean status, Integer providerId, ProvidersDto provider,Integer categoryId,
                       Integer imageId, String imageUrl, Integer subSubCategoriaId, Boolean isOnSale) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.status = status;
        this.providerId = providerId;
        this.provider = provider;
        this.categoryId = categoryId;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.subSubCategoriaId = subSubCategoriaId;
        this.isOnSale = isOnSale;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getSubSubCategoriaId() {
        return subSubCategoriaId;
    }

    public void setSubSubCategoriaId(Integer subSubCategoriaId) {
        this.subSubCategoriaId = subSubCategoriaId;
    }
}
