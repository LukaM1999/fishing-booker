package com.fishingbooker.dto;

import com.fishingbooker.model.ReservationType;

public class EventDTO {
    private String username;
    private ReservationType type;
    private String rentableName;

    public EventDTO(){}

    public EventDTO(String username, ReservationType type, String rentableName) {
        this.username = username;
        this.type = type;
        this.rentableName = rentableName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }
}
