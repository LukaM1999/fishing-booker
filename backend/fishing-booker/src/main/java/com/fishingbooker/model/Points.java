package com.fishingbooker.model;


import javax.persistence.*;

@Entity
public class Points {
    @Id
    private Long id;
    @Column
    private int customerPoints;
    @Column
    private int ownerPoints;
    @Column
    private int systemTax;
    @Column
    private int silver;
    @Column
    private int gold;

    public Points() {
    }

    public Points(int customerPoints, int ownerPoints, int systemTax, int silver, int gold) {
        this.id = 1L;
        this.customerPoints = customerPoints;
        this.ownerPoints = ownerPoints;
        this.systemTax = systemTax;
        this.silver = silver;
        this.gold = gold;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCustomerPoints() {
        return customerPoints;
    }

    public void setCustomerPoints(int customerPoints) {
        this.customerPoints = customerPoints;
    }

    public int getOwnerPoints() {
        return ownerPoints;
    }

    public void setOwnerPoints(int ownerPoints) {
        this.ownerPoints = ownerPoints;
    }

    public int getSystemTax() {
        return systemTax;
    }

    public void setSystemTax(int systemTax) {
        this.systemTax = systemTax;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
