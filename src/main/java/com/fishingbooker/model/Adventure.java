package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Adventure extends Rentable{
    @Column
    private String instructorBio;
    @Column
    private String fishingEquipment;

    @JoinColumn(name = "instructor_username", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Instructor.class, fetch = FetchType.EAGER)
    private Instructor instructor;

    @Column(name = "instructor_username")
    private String instructorUsername;

    public Adventure(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, float price, String additionalServices, float cancellationFee, String instructorBio, String fishingEquipment) {
        super(id, name, country, city, address, promoDescription, capacity, rules, price, additionalServices, cancellationFee);
        this.instructorBio = instructorBio;
        this.fishingEquipment = fishingEquipment;
    }

    public Adventure() {
        super();
    }

    public String getInstructorBio() {
        return instructorBio;
    }

    public void setInstructorBio(String instructorBio) {
        this.instructorBio = instructorBio;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String getInstructorUsername() {
        return instructorUsername;
    }

    public void setInstructorUsername(String instructorUsername) {
        this.instructorUsername = instructorUsername;
    }
}

