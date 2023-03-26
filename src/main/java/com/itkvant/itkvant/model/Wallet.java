package com.itkvant.itkvant.model;

import lombok.*;


import javax.persistence.*;



@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double balance;

    @Builder.Default
    private String currency = Currency.KVANT.name();

}
