package com.fishingbooker.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

public class BoatOwner extends RegisteredUser{

    @OneToMany(mappedBy = "BoatOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Boat> boats;


    public BoatOwner(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role) {
        super(username, password, name, surname, email, address, city, country, phone, role);
    }

    public Collection<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Collection<Boat> boats) {
        this.boats = boats;
    }
}
