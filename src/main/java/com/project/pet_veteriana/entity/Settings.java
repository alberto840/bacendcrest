package com.project.pet_veteriana.entity;
import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Settings")
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id", nullable = false)
    private Integer settingsId;

    @Column(name = "key", nullable = false, length = 150)
    private String key;

    @Column(name = "value", nullable = false, length = 150)
    private String value;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con Rol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    public Settings() {
    }

    public Settings(Integer settingsId, String key, String value, LocalDateTime createdAt, Rol rol) {
        this.settingsId = settingsId;
        this.key = key;
        this.value = value;
        this.createdAt = createdAt;
        this.rol = rol;
    }

    public Integer getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Integer settingsId) {
        this.settingsId = settingsId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
