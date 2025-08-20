package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction_History") // Asegúrate de que el nombre de la tabla sea correcto
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_history_id")
    private Integer transactionHistoryId;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = true) // Relación con Services
    private Services service; // <-- Asegúrate de que este campo exista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true) // Si también manejas productos
    private Products product; // Asumiendo que tienes una entidad Products

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "amount_per_unit")
    private Double amountPerUnit;

    @Column(name = "detail", length = 255)
    private String detail;

    // Constructors
    public TransactionHistory() {
    }

    // Asegúrate de que tu constructor con parámetros en la entidad incluya 'service' y 'product'
    public TransactionHistory(Integer transactionHistoryId, Double totalAmount, String status, LocalDateTime createdAt, Users user, Services service, Products product, Double quantity, Double amountPerUnit, String detail) {
        this.transactionHistoryId = transactionHistoryId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
        this.service = service;
        this.product = product;
        this.quantity = quantity;
        this.amountPerUnit = amountPerUnit;
        this.detail = detail;
    }

    // Getters and Setters
    public Integer getTransactionHistoryId() {
        return transactionHistoryId;
    }

    public void setTransactionHistoryId(Integer transactionHistoryId) {
        this.transactionHistoryId = transactionHistoryId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Services getService() { // Getter para Services
        return service;
    }

    public void setService(Services service) { // Setter para Services
        this.service = service;
    }

    public Products getProduct() { // Getter para Products
        return product;
    }

    public void setProduct(Products product) { // Setter para Products
        this.product = product;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}