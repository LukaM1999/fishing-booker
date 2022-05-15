package com.fishingbooker.dto;

public class ActionDTO {
    private String ownerUsername;
    private String name;

    public ActionDTO() {
    }

    public ActionDTO(String ownerUsername, String name) {
        this.ownerUsername = ownerUsername;
        this.name = name;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
