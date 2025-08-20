package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "price", nullable = false, precision = 10)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "status", nullable = false)
    private Boolean status;    

    // Relación con Providers
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Providers provider;

    // Relación con Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Relación con ImageS3
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "image_id", nullable = true)
    private ImageS3 image;

    // Relación opcional con SubSubCategoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_sub_categoria_id", nullable = true)
    private SubSubCategoria subSubCategoria;

    @Column(name = "is_on_sale", nullable = false)
    private Boolean isOnSale = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Products() {
    }

    public Products(Integer productId, String name, String description, Double price, Integer stock, LocalDateTime createdAt, Boolean status, Providers provider, Category category, ImageS3 image, SubSubCategoria subSubCategoria, Boolean isOnSale) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.status = status;
        this.provider = provider;
        this.category = category;
        this.image = image;
        this.subSubCategoria = subSubCategoria;
        this.isOnSale = isOnSale;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
    // Getter
    public Boolean getIsOnSale() {
        return isOnSale;
    }

    // Setter
    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }
}
