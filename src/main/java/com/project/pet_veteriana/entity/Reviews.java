package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviews_id", nullable = false)
    private Integer reviewsId;

    @Column(name = "rating", nullable = false, precision = 3)
    private Double rating;

    @Column(name = "comment", nullable = false, length = 150)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Relación con Providers
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Providers provider;

    public Reviews() {
    }

    public Reviews(Integer reviewsId, Double rating, String comment, LocalDateTime createdAt, Users user, Providers provider) {
        this.reviewsId = reviewsId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.user = user;
        this.provider = provider;
    }

    public Integer getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Integer reviewsId) {
        this.reviewsId = reviewsId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }
}

