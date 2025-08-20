package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Integer reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private Services service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", referencedColumnName = "availability_id", nullable = false)
    private ServiceAvailability availability;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id", nullable = false)
    private Pets pet;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Reservations() {
    }

    public Reservations(Integer reservationId, Users user, Services service, ServiceAvailability availability,
                        Pets pet, LocalDateTime date, String status, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.user = user;
        this.service = service;
        this.availability = availability;
        this.pet = pet;
        this.date = date;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public ServiceAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(ServiceAvailability availability) {
        this.availability = availability;
    }

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
}
