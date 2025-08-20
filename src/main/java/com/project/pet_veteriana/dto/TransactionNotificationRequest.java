package com.project.pet_veteriana.dto;

public class TransactionNotificationRequest {
    private String email;
    private String buyerName;
    private String detail;
    private Double quantity;
    private Double amountPerUnit;
    private Double totalAmount;

    // Constructor vacío y completo
    public TransactionNotificationRequest() {
    }

    public TransactionNotificationRequest(String email, String buyerName, String detail, Double quantity, Double amountPerUnit, Double totalAmount) {
        this.email = email;
        this.buyerName = buyerName;
        this.detail = detail;
        this.quantity = quantity;
        this.amountPerUnit = amountPerUnit;
        this.totalAmount = totalAmount;
    }

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmountPerUnit() {
        return amountPerUnit;
    }

    public void setAmountPerUnit(Double amountPerUnit) {
        this.amountPerUnit = amountPerUnit;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
