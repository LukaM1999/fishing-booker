package com.fishingbooker.model;

import javax.persistence.*;

@Entity
public class Proba {
    @Id
    @SequenceGenerator(name = "drugSeqGen", sequenceName = "drugSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "drugSeqGen")
    @Column(name="id", unique=true, nullable=false)
    public long id;
    public Proba(){}
    public Proba(long id){
        this.id = id;
    }
}
