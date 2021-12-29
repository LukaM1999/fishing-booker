package com.fishingbooker.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Cottage extends Rentable{
    @Column
    private int rooms;
    @Column
    private int bedsPerRoom;

    public Cottage(long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, boolean freeCancellation, int rooms, int bedsPerRoom) {
        super(id, name, country, city, address, promoDescription, capacity, rules, pricing, freeCancellation);
        this.rooms = rooms;
        this.bedsPerRoom = bedsPerRoom;
    }

    public Cottage() {
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(int bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
    }
}
