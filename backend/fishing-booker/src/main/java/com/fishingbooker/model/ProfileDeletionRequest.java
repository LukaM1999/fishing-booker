package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProfileDeletionRequest {

    @Id
    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String deletionReason;

    public ProfileDeletionRequest(){}

    public ProfileDeletionRequest(String username, String email, String deletionReason) {
        this.username = username;
        this.email = email;
        this.deletionReason = deletionReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeletionReason() {
        return deletionReason;
    }

    public void setDeletionReason(String deletionReason) {
        this.deletionReason = deletionReason;
    }
}
