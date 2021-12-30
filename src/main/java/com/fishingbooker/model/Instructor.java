package com.fishingbooker.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

public class Instructor extends RegisteredUser{
    @OneToMany(mappedBy = "Instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Adventure> adventures;
    @Column
    private String letterOfIntent;
    @Column
    private boolean isActivated;

    public Instructor(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role, String letterOfIntent) {
        super(username, password, name, surname, email, address, city, country, phone, role);
        this.letterOfIntent = letterOfIntent;
        this.isActivated = false;
    }

    public Collection<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(Collection<Adventure> adventures) {
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
