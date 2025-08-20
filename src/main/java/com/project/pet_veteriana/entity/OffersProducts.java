package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Offers_Products")

public class OffersProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offers_products_id", nullable = false)
    private Integer offersProductsId;

    @ManyToOne
    @JoinColumn(name = "Offers_offer_id", referencedColumnName = "offer_id", nullable = false)
    private Offers offer;

    @ManyToOne
    @JoinColumn(name = "Products_product_id", referencedColumnName = "product_id", nullable = false)
    private Products product;

    public OffersProducts() {
    }

    public OffersProducts(Integer offersProductsId, Offers offer, Products product) {
        this.offersProductsId = offersProductsId;
        this.offer = offer;
        this.product = product;
    }

    public Integer getOffersProductsId() {
        return offersProductsId;
    }

    public void setOffersProductsId(Integer offersProductsId) {
        this.offersProductsId = offersProductsId;
    }

    public Offers getOffer() {
        return offer;
    }

    public void setOffer(Offers offer) {
        this.offer = offer;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}