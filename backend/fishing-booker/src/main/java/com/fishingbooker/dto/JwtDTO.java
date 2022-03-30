package com.fishingbooker.dto;

import com.fishingbooker.model.RegisteredUser;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
public class JwtDTO {
    private RegisteredUser user;
    private String accessToken;
    private Long expiresIn;

    public JwtDTO() {
        this.user = null;
        this.accessToken = null;
        this.expiresIn = null;
    }

    public JwtDTO(RegisteredUser user, String accessToken, long expiresIn) {
        this.user = user;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
