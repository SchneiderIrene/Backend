package de.leafgrow.leafgrow_project.security.sec_dto;

public class ChangePasswordRequestDto {
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
