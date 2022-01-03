package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Adventure extends Rentable{
    @Column
    private String instructorBio;
    @Column
    private String fishingEquipment;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    public Adventure(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, float cancellationFee, String instructorBio, String fishingEquipment) {
        super(id, name, country, city, address, promoDescription, capacity, rules, pricing, cancellationFee);
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
}

