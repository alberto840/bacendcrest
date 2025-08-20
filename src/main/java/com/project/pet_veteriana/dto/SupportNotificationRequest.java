package com.project.pet_veteriana.dto;

public class SupportNotificationRequest {
    private String email;
    private String issue;
    private String detail;

    public SupportNotificationRequest() {}

    public SupportNotificationRequest(String email, String issue, String detail) {
        this.email = email;
        this.issue = issue;
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
