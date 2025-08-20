package com.project.pet_veteriana.dto;
import java.time.LocalDateTime;

public class OffersDto {

    private Integer offerId;
    private String name;
    private String description;
    private String discountType;
    private Double discountValue;
    private Boolean isActive;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public OffersDto() {
    }

    public OffersDto(Integer offerId, String name, String description, String discountType, Double discountValue, Boolean isActive, LocalDateTime startDate, LocalDateTime endDate) {
        this.offerId = offerId;
        this.name = name;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
}
