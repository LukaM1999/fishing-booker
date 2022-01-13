package com.fishingbooker.model;

import javax.persistence.*;

@Entity
public class Adventure extends Rentable{
    @Column
    private String instructorBio;
    @Column
    private String fishingEquipment;
    @Column
    private String images;

    @JoinColumn(name = "owner_username", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Instructor.class, fetch = FetchType.EAGER)
    private Instructor instructor;

    @Column(name = "owner_username")
    private String ownerUsername;

    public Adventure(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, float price, String additionalServices, float cancellationFee, String instructorBio, String fishingEquipment, String ownerUsername, String images) {
        super(id, name, country, city, address, promoDescription, capacity, rules, price, additionalServices, cancellationFee);
        this.instructorBio = instructorBio;
        this.fishingEquipment = fishingEquipment;
        this.images = images;
        this.ownerUsername = ownerUsername;
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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String instructorUsername) {
        this.ownerUsername = instructorUsername;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

