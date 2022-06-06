package com.fishingbooker.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @Column
    private Long reservationId;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version = 0L;

    @Column
    private String comment;

    @Column
    private int rentableRating;

    @Column
    private int ownerRating;

    @Column
    private boolean isPublic;

    @Column
    private boolean isApproved;

    @Column
    private String rentableName;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    @Column
    private String ownerUsername;


    public Review(){}

    public Review(Long reservationId, String comment, int rentableRating, int ownerRating) {
        this.reservationId = reservationId;
        this.comment = comment;
        this.isPublic = false;
        this.rentableRating = rentableRating;
        this.ownerRating = ownerRating;
        this.isApproved = false;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long id) {
        this.reservationId = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public int getRentableRating() {
        return rentableRating;
    }

    public void setRentableRating(int rentableRating) {
        this.rentableRating = rentableRating;
    }

    public int getOwnerRating() {
        return ownerRating;
    }

    public void setOwnerRating(int ownerRating) {
        this.ownerRating = ownerRating;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
