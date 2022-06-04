package com.fishingbooker.model;

import javax.persistence.*;

@Entity
@Table
public class SaleSubscription {

    @Id
    @SequenceGenerator(name = "sale_subscription_id_gen", sequenceName = "sale_subscription_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sale_subscription_id_gen")
    private Long id;

    @Column
    private String entityName;

    @Column
    private String ownerUsername;

    @Column
    private String customerUsername;

    @Column
    private String email;

    public SaleSubscription() { }

    public SaleSubscription(String entityName, String ownerUsername, String customerUsername, String email) {
        this.entityName = entityName;
        this.ownerUsername = ownerUsername;
        this.customerUsername = customerUsername;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
