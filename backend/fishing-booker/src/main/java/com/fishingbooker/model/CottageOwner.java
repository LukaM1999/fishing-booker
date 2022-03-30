package com.fishingbooker.model;

import com.fishingbooker.dto.RegistrationDTO;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class CottageOwner extends RegisteredUser{

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Cottage> cottages = new LinkedHashSet<Cottage>();
    @Column
    private String letterOfIntent;

    public CottageOwner(){}

    public CottageOwner(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String letterOfIntent) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.letterOfIntent = letterOfIntent;
    }

    public CottageOwner(RegistrationDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getEmail(), dto.getAddress(), dto.getCity(), dto.getCountry(), dto.getPhone(), dto.getRole());
        this.letterOfIntent = dto.getLetterOfIntent();
    }

    public Set<Cottage> getCottages() {
        return cottages;
    }

    public void setCottages(Set<Cottage> cottages) {
        this.cottages = cottages;
    }

    public String getLetterOfIntent() {
        return letterOfIntent;
    }

    public void setLetterOfIntent(String letterOfIntent) {
        this.letterOfIntent = letterOfIntent;
    }
}
