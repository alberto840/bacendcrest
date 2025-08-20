package com.project.pet_veteriana.dto;

import java.time.LocalTime;

public class ServiceAvailabilityDto {

    private Integer availabilityId;
    private Integer serviceId;
    private LocalTime availableHour;
    private Boolean isReserved;

    public ServiceAvailabilityDto() {
    }

    public ServiceAvailabilityDto(Integer availabilityId, Integer serviceId, LocalTime availableHour, Boolean isReserved) {
        this.availabilityId = availabilityId;
        this.serviceId = serviceId;
        this.availableHour = availableHour;
        this.isReserved = isReserved;
    }

    public Integer getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Integer availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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

    public void setIsRaeserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }
}
