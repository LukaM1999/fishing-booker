package com.fishingbooker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fishingbooker.model.ReservationType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class FreeTermDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private String username;
    private String rentableName;
    private ReservationType type;

    public FreeTermDTO(){}

    public FreeTermDTO(LocalDateTime start, LocalDateTime end, String username, String rentableName, ReservationType type) {
        this.start = start;
        this.end = end;
        this.username = username;
        this.rentableName = rentableName;
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }
}
