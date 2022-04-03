package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Review {

    @Id
    @Column
    private Long reservationId;

    @Column
    private String comment;

    @Column
    private int rentableRating;

    @Column
    private int ownerRating;

    @Column
    private boolean isPublic;

    public Review(){}

    public Review(Long reservationId, String comment, int rentableRating, int ownerRating) {
        this.reservationId = reservationId;
        this.comment = comment;
        this.isPublic = false;
        this.rentableRating = rentableRating;
        this.ownerRating = ownerRating;
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
}
