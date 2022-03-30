package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Subscription {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String entityName;

    @Column
    private String ownerUsername;

    @Column
    private String customerUsername;

    public Subscription() { }

    public Subscription(int id, String entityName, String ownerUsername, String customerUsername) {
        this.id = id;
        this.entityName = entityName;
        this.ownerUsername = ownerUsername;
        this.customerUsername = customerUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }
}
