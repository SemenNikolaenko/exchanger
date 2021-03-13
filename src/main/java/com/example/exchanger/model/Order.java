package com.example.exchanger.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    @Column(name = "from_currency")
    private String fromCurrency;
    @Column(name = "to_currency")
    private String toCurrency;
    @NotNull(message = "amount of exchange can't be null")
    private double amount;
    @Setter(AccessLevel.NONE)
    @Column(name = "exchange_rate")
    @NotNull
    private double exchangeRate;
    @Column(name = "is_finished")
    private boolean isFinished;
    @Column(name = "exchange_action")
    @Enumerated(EnumType.STRING)
    private ExchangeAction exchangeAction;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonManagedReference
    private Account orderCreator;

    public Order(String fromCurrency,
                 String toCurrency,
                 @NotNull(message = "amount of exchange can't be null") double amount,
                 @NotNull double exchangeRate,
                 ExchangeAction exchangeAction,
                 Account orderCreator) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.exchangeRate = exchangeRate;
        this.exchangeAction = exchangeAction;
        this.orderCreator = orderCreator;
    }
}
