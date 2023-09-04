package com.itkvant.itkvant.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private Wallet wallet;

    public Transaction(BigDecimal amount, LocalDateTime dateTime, Wallet wallet) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.wallet = wallet;
    }
}
