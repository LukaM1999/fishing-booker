package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Penalty {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String customerUsername;

    @Column
    private LocalDate dateIssued;

    public Penalty(){}

    public Penalty(Long id, String customerUsername, LocalDate dateIssued) {
        this.id = id;
        this.customerUsername = customerUsername;
        this.dateIssued = dateIssued;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }
}
