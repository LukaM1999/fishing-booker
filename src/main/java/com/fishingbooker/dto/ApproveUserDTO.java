package com.fishingbooker.dto;

public class ApproveUserDTO {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String letterOfIntent;
    private String role;

    public ApproveUserDTO(){}

    public ApproveUserDTO(String username, String name, String surname, String email, String letterOfIntent, String role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.letterOfIntent = letterOfIntent;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLetterOfIntent() {
        return letterOfIntent;
    }

    public void setLetterOfIntent(String letterOfIntent) {
        this.letterOfIntent = letterOfIntent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
