package com.org.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "incident_type")
    private String incidentType;

    @Column(name = "filed_at")
    private LocalDateTime filedAt;

    @Column(name = "status")
    private String status;

    @PrePersist
    public void prePersist() {
        this.filedAt = LocalDateTime.now();
        this.status = "pending";
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public LocalDateTime getFiledAt() { return filedAt; }
    public void setFiledAt(LocalDateTime filedAt) { this.filedAt = filedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}