package com.project.pet_veteriana.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "service_availability")
public class ServiceAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    private Integer availabilityId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    @Column(name = "available_hour", nullable = false)
    private LocalTime availableHour;

    @Column(name = "is_reserved", nullable = false)
    private Boolean isReserved = false; // Nuevo campo para marcar si está reservado

    public ServiceAvailability() {
    }

    public ServiceAvailability(Integer availabilityId, Services service, LocalTime availableHour, Boolean isReserved) {
        this.availabilityId = availabilityId;
        this.service = service;
        this.availableHour = availableHour;
        this.isReserved = isReserved;
    }

    public Integer getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Integer availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public LocalTime getAvailableHour() {
        return availableHour;
    }

    public void setAvailableHour(LocalTime availableHour) {
        this.availableHour = availableHour;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }
}
