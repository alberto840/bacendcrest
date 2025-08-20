package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Services")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;

    @Column(name = "service_name", nullable = false, length = 250)
    private String serviceName;

    @Column(name = "price", nullable = false, precision = 10)
    private Double price;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "tipo_atencion", nullable = false, length = 100)
    private String tipoAtencion;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Providers provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = true)
    private ImageS3 image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_sub_categoria_id", nullable = true)
    private SubSubCategoria subSubCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = true)
    private Category category;

    @Column(name = "is_on_sale", nullable = false)
    private Boolean isOnSale = false;


    public Services() {
    }

    public Services(Integer serviceId, String serviceName, Double price, Integer duration, String description, String tipoAtencion, LocalDateTime createdAt, Boolean status, Providers provider, ImageS3 image, SubSubCategoria subSubCategoria, Category category, Boolean isOnSale) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.tipoAtencion = tipoAtencion;
        this.createdAt = createdAt;
        this.status = status;
        this.provider = provider;
        this.image = image;
        this.subSubCategoria = subSubCategoria;
        this.category = category;
        this.isOnSale = isOnSale;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    public void setTipoAtencion(String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public ImageS3 getImage() {
        return image;
    }

    public void setImage(ImageS3 image) {
        this.image = image;
    }

    public SubSubCategoria getSubSubCategoria() {
        return subSubCategoria;
    }

    public void setSubSubCategoria(SubSubCategoria subSubCategoria) {
        this.subSubCategoria = subSubCategoria;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getOnSale() {
        return isOnSale;
    }


    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }
}