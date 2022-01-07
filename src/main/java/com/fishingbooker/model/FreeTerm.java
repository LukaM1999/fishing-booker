package com.fishingbooker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FreeTerm {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Column
    private String entityName;

    @Column
    private String ownerUsername;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public FreeTerm(){}

    public FreeTerm(Long id, ReservationType type, String entityName, String ownerUsername, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.type = type;
        this.entityName = entityName;
        this.ownerUsername = ownerUsername;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
