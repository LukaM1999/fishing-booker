package com.fishingbooker.dto;

import com.fishingbooker.model.ReservationType;

public class ReservationHistoryDTO {
    private ReservationType type;
    private String username;
    private boolean isCustomer;

    public ReservationHistoryDTO() {}

    public ReservationHistoryDTO(ReservationType type, String username, boolean isCustomer) {
        this.type = type;
        this.username = username;
        this.isCustomer = isCustomer;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(boolean customer) {
        isCustomer = customer;
    }
}
