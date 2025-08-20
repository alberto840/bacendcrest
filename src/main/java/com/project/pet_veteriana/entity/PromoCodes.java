package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Promo_Codes")
public class PromoCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id", nullable = false)
    private Integer promoId;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType;

    @Column(name = "discount_value", nullable = false, precision = 10)
    private Double discountValue;

    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;

    @Column(name = "current_uses", nullable = false)
    private Integer currentUses;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Providers provider;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PromoCodes() {
    }

    public PromoCodes(Integer promoId, String code, String description, String discountType, Double discountValue, Integer maxUses, Integer currentUses, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive, Providers provider, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        this.provider = provider;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
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
}
