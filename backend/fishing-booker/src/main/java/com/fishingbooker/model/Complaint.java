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
    private String issuerUsername;

    @Column
    private String subjectUsername;

    @Column
    private String complaint;

    @Column
    private boolean isReviewed;

    @Column
    private boolean isFromCustomer;

    @Column
    private boolean isForPenalty;

    public Complaint() {}

    public Complaint(Long reservationId, String issuerUsername, String subjectUsername, String complaint, boolean isReviewed, boolean isFromCustomer, boolean isForPenalty) {
        this.reservationId = reservationId;
        this.issuerUsername = issuerUsername;
        this.subjectUsername = subjectUsername;
        this.complaint = complaint;
        this.isReviewed = isReviewed;
        this.isFromCustomer = isFromCustomer;
        this.isForPenalty = isForPenalty;
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

    public boolean isForPenalty() {
        return isForPenalty;
    }

    public void setForPenalty(boolean forPenalty) {
        isForPenalty = forPenalty;
    }

    public String getIssuerUsername() {
        return issuerUsername;
    }

    public void setIssuerUsername(String issuerUsername) {
        this.issuerUsername = issuerUsername;
    }

    public String getSubjectUsername() {
        return subjectUsername;
    }

    public void setSubjectUsername(String subjectUsername) {
        this.subjectUsername = subjectUsername;
    }
}
