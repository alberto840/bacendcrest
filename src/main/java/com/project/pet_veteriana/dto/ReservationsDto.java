package com.project.pet_veteriana.dto;

import java.time.LocalDateTime;

public class ReservationsDto {

    private Integer reservationId;
    private Integer userId; // Se mantiene por si solo se necesita el ID
    private UsersDto user; // <-- Nuevo: Objeto DTO de Users
    private Integer serviceId; // Se mantiene por si solo se necesita el ID
    private ServicesDto service; // <-- Nuevo: Objeto DTO de Services
    private Integer availabilityId; // Se mantiene por si solo se necesita el ID
    private ServiceAvailabilityDto availability; // <-- Nuevo: Objeto DTO de Availability
    private Integer petId; // Se mantiene por si solo se necesita el ID
    private PetsDto pet; // <-- Nuevo: Objeto DTO de Pets
    private LocalDateTime date;
    private String status;
    private LocalDateTime createdAt;

    public ReservationsDto() {
    }

    // Constructor con parámetros actualizado
    public ReservationsDto(Integer reservationId, Integer userId, UsersDto user, Integer serviceId, ServicesDto service,
                           Integer availabilityId, ServiceAvailabilityDto availability, Integer petId, PetsDto pet,
                           LocalDateTime date, String status, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.user = user; // Asignar el objeto UsersDto
        this.serviceId = serviceId;
        this.service = service; // Asignar el objeto ServicesDto
        this.availabilityId = availabilityId;
        this.availability = availability; // Asignar el objeto AvailabilityDto
        this.petId = petId;
        this.pet = pet; // Asignar el objeto PetsDto
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UsersDto getUser() { // Getter para UsersDto
        return user;
    }

    public void setUser(UsersDto user) { // Setter para UsersDto
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

    public Integer getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Integer availabilityId) {
        this.availabilityId = availabilityId;
    }

    public ServiceAvailabilityDto getAvailability() { // Getter para AvailabilityDto
        return availability;
    }

    public void setAvailability(ServiceAvailabilityDto availability) { // Setter para AvailabilityDto
        this.availability = availability;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public PetsDto getPet() { // Getter para PetsDto
        return pet;
    }

    public void setPet(PetsDto pet) { // Setter para PetsDto
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