package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class CodesUsersDto {
    private Integer idCodes;
    private Integer userId;
    private Integer promoId;
    private String code;
    private String description;
    private String discountType;
    private Double discountValue;
    private LocalDateTime endDate; // Fecha de expiración

    public CodesUsersDto() {
    }

    public CodesUsersDto(Integer idCodes, Integer userId, Integer promoId, String code, String description, String discountType, Double discountValue, LocalDateTime endDate) {
        this.idCodes = idCodes;
        this.userId = userId;
        this.promoId = promoId;
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.endDate = endDate;
    }

    // Getters y Setters
    public Integer getIdCodes() {
        return idCodes;
    }

    public void setIdCodes(Integer idCodes) {
        this.idCodes = idCodes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
