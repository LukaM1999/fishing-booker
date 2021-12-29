package com.fishingbooker.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

public class CottageOwner extends RegisteredUser{

    @OneToMany(mappedBy = "CottageOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Cottage> cottages;


    public CottageOwner(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role) {
        super(username, password, name, surname, email, address, city, country, phone, role);
    }

    public Collection<Cottage> getCottages() {
        return cottages;
    }

    public void setCottages(Collection<Cottage> cottages) {
        this.cottages = cottages;
    }
}
