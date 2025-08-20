package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "Spreciality_Providers")
public class SprecialityProviders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sp_pro", nullable = false)
    private Integer idSpPro;

    // Relación con Specialty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    // Relación con Providers
    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Providers provider;


    public SprecialityProviders() {
    }

    public SprecialityProviders(Integer idSpPro, Specialty specialty, Providers provider) {
        this.idSpPro = idSpPro;
        this.specialty = specialty;
        this.provider = provider;
    }

    public Integer getIdSpPro() {
        return idSpPro;
    }

    public void setIdSpPro(Integer idSpPro) {
        this.idSpPro = idSpPro;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }
}
