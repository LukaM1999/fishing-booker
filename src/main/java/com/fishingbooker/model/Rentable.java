package com.fishingbooker.model;

import javax.persistence.*;
import java.util.Collection;

public class Rentable {

    @Id
    private long id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private String address;
    @Column
    private String promoDescription;
    @OneToMany(mappedBy = "Rentable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<String> photos;
    @Column
    private int capacity;
    @OneToMany(mappedBy = "Rentable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<FreeTerm> freeTerms;
    @Column
    private String rules;
    @Column
    private String pricing;
    @Column
    private boolean freeCancellation;

    public Rentable(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, boolean freeCancellation) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.rules = rules;
        this.pricing = pricing;
        this.freeCancellation = freeCancellation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

    public Collection<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<String> photos) {
        this.photos = photos;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Collection<FreeTerm> getFreeTerms() {
        return freeTerms;
    }

    public void setFreeTerms(Collection<FreeTerm> freeTerms) {
        this.freeTerms = freeTerms;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public boolean isFreeCancellation() {
        return freeCancellation;
    }

    public void setFreeCancellation(boolean freeCancellation) {
        this.freeCancellation = freeCancellation;
    }
}
