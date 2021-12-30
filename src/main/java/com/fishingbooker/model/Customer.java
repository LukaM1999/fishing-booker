package com.fishingbooker.model;

import com.fishingbooker.dto.RegistrationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends RegisteredUser{

    @Column
    private String verificationToken;

    public Customer() {
        super();
    }

    public Customer(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String verificationToken) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.verificationToken = verificationToken;
    }

    public Customer(RegistrationDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getEmail(), dto.getAddress(), dto.getCity(), dto.getCountry(), dto.getPhone(), dto.getRole());
        this.verificationToken = dto.getVerificationToken();
    }


    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
