package com.project.pet_veteriana.dto;


public class OffersProductsDto {

    private Integer offersProductsId;
    private Integer offerId;
    private Integer productId;

    public OffersProductsDto() {
    }

    public OffersProductsDto(Integer offersProductsId, Integer offerId, Integer productId) {
        this.offersProductsId = offersProductsId;
        this.offerId = offerId;
        this.productId = productId;
    }

    public Integer getOffersProductsId() {
        return offersProductsId;
    }

    public void setOffersProductsId(Integer offersProductsId) {
        this.offersProductsId = offersProductsId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
