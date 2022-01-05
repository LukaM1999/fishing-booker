package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @Column
    private boolean isActivated;

    public Instructor(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String letterOfIntent) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.letterOfIntent = letterOfIntent;
        this.isActivated = false;
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

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }
}
