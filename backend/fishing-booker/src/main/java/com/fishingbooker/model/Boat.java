package com.fishingbooker.model;

import javax.persistence.*;

@Entity
public class Boat extends Rentable{

    @Column
    private String boatType;
    @Column
    private float length;
    @Column
    private int motors;
    @Column
    private float power;
    @Column
    private float maxSpeed;
    @Column
    private boolean gps;
    @Column
    private boolean radar;
    @Column
    private boolean vhfRadio;
    @Column
    private boolean fishFinder;
    @Column
    private String fishingEquipment;
    @Column
    private String images;

    @JoinColumn(name = "owner_username", insertable = false, updatable = false)
    @ManyToOne(targetEntity = BoatOwner.class, fetch = FetchType.EAGER)
    private BoatOwner boatOwner;

    @Column(name = "owner_username")
    private String ownerUsername;

    public Boat(Long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, float price, String additionalServices, float cancellationFee, String boatType, float length, int motors, float power, float maxSpeed, boolean gps, boolean radar, boolean vhfRadio, boolean fishFinder, String fishingEquipment, String images, String ownerUsername) {
        super(id, name, country, city, address, promoDescription, capacity, rules, price, additionalServices, cancellationFee);
        this.boatType = boatType;
        this.length = length;
        this.motors = motors;
        this.power = power;
        this.maxSpeed = maxSpeed;
        this.gps = gps;
        this.radar = radar;
        this.vhfRadio = vhfRadio;
        this.fishFinder = fishFinder;
        this.fishingEquipment = fishingEquipment;
        this.images = images;
        this.ownerUsername = ownerUsername;
    }

    public Boat() {
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public int getMotors() {
        return motors;
    }

    public void setMotors(int motors) {
        this.motors = motors;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isRadar() {
        return radar;
    }

    public void setRadar(boolean radar) {
        this.radar = radar;
    }

    public boolean isVhfRadio() {
        return vhfRadio;
    }

    public void setVhfRadio(boolean vhfRadio) {
        this.vhfRadio = vhfRadio;
    }

    public boolean isFishFinder() {
        return fishFinder;
    }

    public void setFishFinder(boolean fishFinder) {
        this.fishFinder = fishFinder;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public BoatOwner getBoatOwner() {
        return boatOwner;
    }

    public void setBoatOwner(BoatOwner boatOwner) {
        this.boatOwner = boatOwner;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String boatOwnerUsername) { this.ownerUsername = boatOwnerUsername; }
}
