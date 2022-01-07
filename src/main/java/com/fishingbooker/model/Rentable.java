package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.Collection;

@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Rentable {
    @Id
    @Column
    @SequenceGenerator(name = "rentable_id_gen", sequenceName = "rentable_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rentable_id_gen")
    private Long id;
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
    @Column
    private int capacity;
    @Column
    private String rules;
    @Column
    private String pricing;
    @Column
    private float cancellationFee;

    public Rentable(Long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, float cancellationFee) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.rules = rules;
        this.pricing = pricing;
        this.cancellationFee = cancellationFee;
    }

    public Rentable() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }
}
