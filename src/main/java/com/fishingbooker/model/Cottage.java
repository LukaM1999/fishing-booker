package com.fishingbooker.model;

import javax.persistence.*;

@Entity
public class Cottage extends Rentable{
    @Column
    private int rooms;
    @Column
    private int bedsPerRoom;
    @Column
    private String images;

    @JoinColumn(name = "owner_username", insertable = false, updatable = false)
    @ManyToOne(targetEntity = CottageOwner.class, fetch = FetchType.LAZY)
    private CottageOwner owner;

    @Column(name = "owner_username")
    private String ownerUsername;

    public Cottage(Long id, String name, String country, String city, String address, String promoDescription, int capacity, String rules, String pricing, float cancellationFee, int rooms, int bedsPerRoom, String ownerUsername, String images) {
        super(id, name, country, city, address, promoDescription, capacity, rules, pricing, cancellationFee);
        this.rooms = rooms;
        this.bedsPerRoom = bedsPerRoom;
        this.images = images;
        this.ownerUsername = ownerUsername;
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

    public CottageOwner getOwner() {
        return owner;
    }

    public void setOwner(CottageOwner owner) {
        this.owner = owner;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
