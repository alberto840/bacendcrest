package com.project.pet_veteriana.dto;


import java.time.LocalDateTime;

public class SettingsDto {

    private Integer settingsId;
    private String key;
    private String value;
    private LocalDateTime createdAt;
    private Integer rolId;

    public SettingsDto() {
    }

    public SettingsDto(Integer settingsId, String key, String value, LocalDateTime createdAt, Integer rolId) {
        this.settingsId = settingsId;
        this.key = key;
        this.value = value;
        this.createdAt = createdAt;
        this.rolId = rolId;
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

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
}
