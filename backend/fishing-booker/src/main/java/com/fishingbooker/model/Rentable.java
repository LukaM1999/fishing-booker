package com.fishingbooker.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
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
    private float price;
    @Column
    private String additionalServices;
    @Column
    private float cancellationFee;

    @Column
    @ColumnDefault("0")
    private int timesRated;

    @Column
    @ColumnDefault("0")
    private double averageRating;

    public Rentable(Long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, float price, String additionalServices, float cancellationFee) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.address = address;
        this.promoDescription = promoDescription;
        this.capacity = capacity;
        this.rules = rules;
        this.price = price;
        this.additionalServices = additionalServices;
        this.cancellationFee = cancellationFee;
        this.timesRated = 0;
        this.averageRating = 0;
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

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public String getAdditionalServices() { return additionalServices; }

    public void setAdditionalServices(String additionalServices) { this.additionalServices = additionalServices; }

    public float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public int getTimesRated() {
        return timesRated;
    }

    public void setTimesRated(int timesRated) {
        this.timesRated = timesRated;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rentable)) return false;
        Rentable rentable = (Rentable) o;
        return id.equals(rentable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
