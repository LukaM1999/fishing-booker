package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @SequenceGenerator(name = "reservation_id_gen", sequenceName = "reservation_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id_gen")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @Column
    private String additionalServices;

    @Column
    private int guests;

    @Column
    private boolean isCancelled;

    @Column
    private boolean isDeal;

    @Column
    private int salePercent;

    @Column
    private double price;

    @Column
    private boolean isReviewed;

    @Column
    private boolean complaintExists;

    public Reservation() { }

    public Reservation(Long id, ReservationType type, String name, String ownerUsername, String customerUsername,
                       LocalDateTime startTime, LocalDateTime endTime, String additionalServices, int guests, boolean isDeal, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.customerUsername = customerUsername;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;
        this.additionalServices = additionalServices;
        this.guests = guests;
        this.isDeal = isDeal;
        this.price = price;
        this.isReviewed = false;
        this.complaintExists = false;
        this.salePercent = 0;
    }

    public Reservation(Long id, ReservationType type, String name, String ownerUsername, LocalDateTime startTime,
                       LocalDateTime endTime, String additionalServices, int guests, int salePercent, double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.customerUsername = null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;
        this.additionalServices = additionalServices;
        this.guests = guests;
        this.isDeal = true;
        this.price = price;
        this.isReviewed = false;
        this.complaintExists = false;
        this.salePercent = salePercent;
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

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
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

    public boolean isComplaintExists() {
        return complaintExists;
    }

    public void setComplaintExists(boolean complaintExists) {
        this.complaintExists = complaintExists;
    }

    public int getSalePercent() {return salePercent; }

    public void setSalePercent(int salePercent) {this.salePercent = salePercent; }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }
}
