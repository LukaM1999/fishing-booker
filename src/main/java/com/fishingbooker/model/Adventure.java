package com.fishingbooker.model;

import javax.persistence.Column;

/*
● kratku biografiju instruktora,
● koja pecaroška oprema dolazi uz rezervaciju (ako klijent ne ponese svoju),
 */
public class Adventure extends Rentable{
    @Column
    private String instructorBio;
    @Column
    private String fishingEquipment;

    public Adventure(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, boolean freeCancellation, String instructorBio, String fishingEquipment) {
        super(id, name, country, city, address, promoDescription, capacity, rules, pricing, freeCancellation);
        this.instructorBio = instructorBio;
        this.fishingEquipment = fishingEquipment;
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
}

