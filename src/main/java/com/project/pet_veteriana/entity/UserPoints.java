package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "User_points")
public class UserPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_points_id", nullable = false)
    private Integer userPointsId;

    @Column(name = "points", nullable = false, precision = 3)
    private Double points;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public UserPoints() {
    }

    public UserPoints(Integer userPointsId, Double points, String description, LocalDateTime createdAt, Users user) {
        this.userPointsId = userPointsId;
        this.points = points;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getUserPointsId() {
        return userPointsId;
    }

    public void setUserPointsId(Integer userPointsId) {
        this.userPointsId = userPointsId;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
