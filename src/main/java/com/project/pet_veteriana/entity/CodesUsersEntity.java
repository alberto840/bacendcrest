package com.project.pet_veteriana.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Codes_Users")

public class CodesUsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codes")
    private Integer idCodes;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "promo_id", nullable = false)
    private PromoCodes promo;

    public CodesUsersEntity() {
    }

    public CodesUsersEntity(Integer idCodes, Users user, PromoCodes promo) {
        this.idCodes = idCodes;
        this.user = user;
        this.promo = promo;
    }

    public Integer getIdCodes() {
        return idCodes;
    }

    public void setIdCodes(Integer idCodes) {
        this.idCodes = idCodes;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public PromoCodes getPromo() {
        return promo;
    }

    public void setPromo(PromoCodes promo) {
        this.promo = promo;
    }
}
