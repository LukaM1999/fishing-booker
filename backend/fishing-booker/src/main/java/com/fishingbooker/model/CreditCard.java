package com.fishingbooker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @Id
    @SequenceGenerator(
            name = "credit_card_sequence_generator",
            sequenceName = "credit_card_sequence"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_sequence_generator")
    @Column
    public Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(64)")
    public String pan;

    @Column(columnDefinition = "VARCHAR(64)")
    public String securityCode;

    @Column
    public String cardHolderName;

    @Column
    //@DateTimeFormat(pattern = "MM/yyyy")
    public Date validThru = new Date();

    @OneToOne(mappedBy = "creditCard", cascade = {CascadeType.ALL})
    public RegisteredUser user;
}
