package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class TransactionHistoryDto {

    private Integer transactionHistoryId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private Integer userId; // Se mantiene por si solo se necesita el ID
    private UsersDto user;
    private Integer serviceId; // Se mantiene por si solo se necesita el ID
    private ServicesDto service; // <-- Nuevo: Objeto DTO de Services

    private Integer productId; // Se mantiene por si solo se necesita el ID
    private ProductsDto product; // <-- Nuevo: Objeto DTO de Products

    private Double quantity;
    private Double amountPerUnit;
    private String detail;

    public TransactionHistoryDto() {
    }

    // Constructor con parámetros actualizado
    public TransactionHistoryDto(Integer transactionHistoryId, Double totalAmount, String status,
                                 LocalDateTime createdAt, Integer userId,UsersDto user, Integer serviceId, ServicesDto service,
                                 Integer productId, ProductsDto product, Double quantity, Double amountPerUnit, String detail) {
        this.transactionHistoryId = transactionHistoryId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId; // Se mantiene por si solo se necesita el ID
        this.user = user;
        this.serviceId = serviceId;
        this.service = service; // Asignar el objeto ServicesDto
        this.productId = productId;
        this.product = product; // Asignar el objeto ProductsDto
        this.quantity = quantity;
        this.amountPerUnit = amountPerUnit;
        this.detail = detail;
    }

    // Getters y Setters existentes
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

    public Integer getUserId() {
        return userId; // Se mantiene por si solo se necesita el ID
    }

    public void setUserId(Integer userId) {
        this.userId = userId; // Se mantiene por si solo se necesita el ID
    }

    public UsersDto getUser() {
        return user;
    }

    public void setUser(UsersDto user) {
        this.user = user;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public ServicesDto getService() { // Getter para ServicesDto
        return service;
    }

    public void setService(ServicesDto service) { // Setter para ServicesDto
        this.service = service;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public ProductsDto getProduct() { // Getter para ProductsDto
        return product;
    }

    public void setProduct(ProductsDto product) { // Setter para ProductsDto
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