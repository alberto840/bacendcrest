package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "Offers")

public class Offers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id", nullable = false)
    private Integer offerId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType;

    @Column(name = "discount_value", nullable = false, precision = 10)
    private Double discountValue;  // Manteniendo como Double y sin scale

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public Offers() {
    }

    public Offers(Integer offerId, String name, String description, String discountType, Double discountValue, Boolean isActive, LocalDateTime startDate, LocalDateTime endDate) {
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
