package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ReviewsDto {

    private Integer reviewsId;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;
    private Integer userId;
    private Integer providerId;

    public ReviewsDto() {
    }

    public ReviewsDto(Integer reviewsId, Double rating, String comment, LocalDateTime createdAt, Integer userId, Integer providerId) {
        this.reviewsId = reviewsId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.userId = userId;
        this.providerId = providerId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
