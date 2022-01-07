package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Comment {

    @Id
    @Column
    private Long reservationId;

    @Column
    private String comment;

    @Column
    private boolean isPublic;

    public Comment(){}

    public Comment(Long reservationId, String comment) {
        this.reservationId = reservationId;
        this.comment = comment;
        this.isPublic = false;
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
}
