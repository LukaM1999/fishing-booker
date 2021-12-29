package com.fishingbooker.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

public class Instructor extends RegisteredUser{
    @OneToMany(mappedBy = "Instructor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Adventure> adventures;

    public Instructor(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role) {
        super(username, password, name, surname, email, address, city, country, phone, role);
    }

    public Collection<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(Collection<Adventure> adventures) {
        this.adventures = adventures;
    }
}
