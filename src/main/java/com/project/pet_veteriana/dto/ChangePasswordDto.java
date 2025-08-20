package com.project.pet_veteriana.dto;

public class ChangePasswordDto {

    private String oldPassword;
    private String newPassword;

    // Getters y Setters

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
