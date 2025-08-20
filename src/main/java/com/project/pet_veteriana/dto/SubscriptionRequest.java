package com.project.pet_veteriana.dto;

public class SubscriptionRequest {

    private String email;
    private String sellerName;

    // Constructor vacío (necesario para la deserialización JSON)
    public SubscriptionRequest() {}

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
