package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserPoints {

    @Id
    @Column
    private String username;

    @Column
    private int points;

    public UserPoints(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public UserPoints() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
