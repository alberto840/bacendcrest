package com.project.pet_veteriana.dto;


public class SprecialityProvidersDto {

    private Integer idSpPro;
    private Integer specialtyId;
    private Integer providerId;

    public SprecialityProvidersDto() {
    }

    public SprecialityProvidersDto(Integer idSpPro, Integer specialtyId, Integer providerId) {
        this.idSpPro = idSpPro;
        this.specialtyId = specialtyId;
        this.providerId = providerId;
    }

    public Integer getIdSpPro() {
        return idSpPro;
    }

    public void setIdSpPro(Integer idSpPro) {
        this.idSpPro = idSpPro;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
}
