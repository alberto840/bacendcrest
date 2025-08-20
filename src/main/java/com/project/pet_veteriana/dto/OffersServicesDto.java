package com.project.pet_veteriana.dto;

public class OffersServicesDto {

    private Integer offersServicesId;
    private Integer serviceId;
    private Integer offerId;

    public OffersServicesDto() {
    }

    public OffersServicesDto(Integer offersServicesId, Integer serviceId, Integer offerId) {
        this.offersServicesId = offersServicesId;
        this.serviceId = serviceId;
        this.offerId = offerId;
    }

    public Integer getOffersServicesId() {
        return offersServicesId;
    }

    public void setOffersServicesId(Integer offersServicesId) {
        this.offersServicesId = offersServicesId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }
}
