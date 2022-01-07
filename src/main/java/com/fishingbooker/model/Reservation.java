package com.fishingbooker.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationType type;

    @Column
    private String name;

    @Column
    private String ownerUsername;

    @Column
    private String customerUsername;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private String additionalServices;

    @Column
    private boolean isCancelled;

    @Column
    private boolean isDeal;

    @Column
    private double price;

    @Column
    private int rating;

    @Column
    private boolean complaintExists;

    public Reservation() { }

    public Reservation(Long id, ReservationType type, String name, String ownerUsername, String customerUsername,
                       LocalDateTime startTime, LocalDateTime endTime, String additionalServices, boolean isDeal, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.customerUsername = customerUsername;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;
        this.additionalServices = additionalServices;
        this.isDeal = isDeal;
        this.price = price;
        this.rating = 0;
        this.complaintExists = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationType getType() {
        return type;
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        this.additionalServices = additionalServices;
    }

    public boolean isDeal() {
        return isDeal;
    }

    public void setDeal(boolean deal) {
        isDeal = deal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isComplaintExists() {
        return complaintExists;
    }

    public void setComplaintExists(boolean complaintExists) {
        this.complaintExists = complaintExists;
    }
}
