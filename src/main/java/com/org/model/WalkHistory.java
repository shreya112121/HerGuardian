package com.org.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "walk_history")
public class WalkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "status")
    private String status;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "destination")
    private String destination;

    @PrePersist
    public void prePersist() {
        this.startTime = LocalDateTime.now();
        this.status = "ongoing";
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}