package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Offers_Services")
public class OffersServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offers_services_id", nullable = false)
    private Integer offersServicesId;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private Services service;

    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id", nullable = false)
    private Offers offer;

    public OffersServices() {
    }

    public OffersServices(Integer offersServicesId, Services service, Offers offer) {
        this.offersServicesId = offersServicesId;
        this.service = service;
        this.offer = offer;
    }

    public Integer getOffersServicesId() {
        return offersServicesId;
    }

    public void setOffersServicesId(Integer offersServicesId) {
        this.offersServicesId = offersServicesId;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Offers getOffer() {
        return offer;
    }

    public void setOffer(Offers offer) {
        this.offer = offer;
    }
}