package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Complaint {

    @Id
    @Column
    private Long reservationId;

    @Column
    private String complaint;

    @Column
    private boolean isReviewed;

    @Column
    private boolean isFromCustomer;

    public Complaint() {}

    public Complaint(Long reservationId, String complaint, boolean isFromCustomer) {
        this.reservationId = reservationId;
        this.complaint = complaint;
        this.isReviewed = false;
        this.isFromCustomer = isFromCustomer;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isFromCustomer() {
        return isFromCustomer;
    }

    public void setFromCustomer(boolean fromCustomer) {
        isFromCustomer = fromCustomer;
    }
}
