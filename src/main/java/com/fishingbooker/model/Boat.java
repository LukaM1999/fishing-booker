package com.fishingbooker.model;

import javax.persistence.Column;

public class Boat extends Rentable{

    @Column
    private String boatType;
    @Column
    private float lenght;
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
    private boolean fishfinder;
    @Column
    private String fishingEquipment;

    public Boat(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, boolean freeCancellation, String boatType, float lenght, int motors, float power, float maxSpeed, boolean gps, boolean radar, boolean vhfRadio, boolean fishfinder, String fishingEquipment) {
        super(id, name, country, city, address, promoDescription, capacity, rules, pricing, freeCancellation);
        this.boatType = boatType;
        this.lenght = lenght;
        this.motors = motors;
        this.power = power;
        this.maxSpeed = maxSpeed;
        this.gps = gps;
        this.radar = radar;
        this.vhfRadio = vhfRadio;
        this.fishfinder = fishfinder;
        this.fishingEquipment = fishingEquipment;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public float getLenght() {
        return lenght;
    }

    public void setLenght(float lenght) {
        this.lenght = lenght;
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

    public boolean isFishfinder() {
        return fishfinder;
    }

    public void setFishfinder(boolean fishfinder) {
        this.fishfinder = fishfinder;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }
}
