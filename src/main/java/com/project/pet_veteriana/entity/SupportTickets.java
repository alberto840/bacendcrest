package com.project.pet_veteriana.entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Support_tickets")
public class SupportTickets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_tickets_id", nullable = false)
    private Integer supportTicketsId;

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relación con User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    public SupportTickets() {
    }

    public SupportTickets(Integer supportTicketsId, String subject, String description, String status, LocalDateTime updatedAt, LocalDateTime createdAt, Users user) {
        this.supportTicketsId = supportTicketsId;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getSupportTicketsId() {
        return supportTicketsId;
    }

    public void setSupportTicketsId(Integer supportTicketsId) {
        this.supportTicketsId = supportTicketsId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
