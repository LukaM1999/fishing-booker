package com.fishingbooker.dto;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
public class JwtDTO {

    private String accessToken;
    private Long expiresIn;

    public JwtDTO() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public JwtDTO(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
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
