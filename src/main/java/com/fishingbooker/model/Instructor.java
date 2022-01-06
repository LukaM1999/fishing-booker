package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fishingbooker.dto.RegistrationDTO;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Instructor extends RegisteredUser{

    @JsonManagedReference
    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Adventure> adventures = new LinkedHashSet<>();
    @Column
    private String letterOfIntent;

    public Instructor(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String letterOfIntent) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.letterOfIntent = letterOfIntent;
    }

    public Instructor(RegistrationDTO dto){
        super(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getEmail(), dto.getAddress(), dto.getCity(), dto.getCountry(), dto.getPhone(), dto.getRole());
        this.letterOfIntent = dto.getLetterOfIntent();
    }

    public Instructor() {
        super();
    }

    public Set<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(Set<Adventure> adventures) {
        this.adventures = adventures;
    }

    public String getLetterOfIntent() {
        return letterOfIntent;
    }

    public void setLetterOfIntent(String letterOfIntent) {
        this.letterOfIntent = letterOfIntent;
    }

}
