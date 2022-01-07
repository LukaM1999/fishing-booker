package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fishingbooker.dto.RegistrationDTO;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class BoatOwner extends RegisteredUser{

    @OneToMany(mappedBy = "boatOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Boat> boats = new LinkedHashSet<Boat>();
    @Column
    private String letterOfIntent;

    public BoatOwner(){}

    public BoatOwner(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String letterOfIntent) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.letterOfIntent = letterOfIntent;
    }

    public BoatOwner(RegistrationDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getEmail(), dto.getAddress(), dto.getCity(), dto.getCountry(), dto.getPhone(), dto.getRole());
        this.letterOfIntent = dto.getLetterOfIntent();
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public String getLetterOfIntent() {
        return letterOfIntent;
    }

    public void setLetterOfIntent(String letterOfIntent) {
        this.letterOfIntent = letterOfIntent;
    }

}
