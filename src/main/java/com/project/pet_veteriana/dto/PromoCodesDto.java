package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class PromoCodesDto {

    private Integer promoId;
    private String code;
    private String description;
    private String discountType;
    private Double discountValue;
    private Integer maxUses;
    private Integer currentUses;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Integer providerId;
    private LocalDateTime createdAt;

    public PromoCodesDto() {
    }

    public PromoCodesDto(Integer promoId, String code, String description, String discountType, Double discountValue, Integer maxUses, Integer currentUses, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive, Integer providerId, LocalDateTime createdAt) {
        this.promoId = promoId;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxUses = maxUses;
        this.currentUses = currentUses;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.providerId = providerId;
        this.createdAt = createdAt;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUses) {
        this.maxUses = maxUses;
    }

    public Integer getCurrentUses() {
        return currentUses;
    }

    public void setCurrentUses(Integer currentUses) {
        this.currentUses = currentUses;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
