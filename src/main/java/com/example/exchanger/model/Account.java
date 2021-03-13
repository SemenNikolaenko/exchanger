package com.example.exchanger.model;


import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@NamedEntityGraph(name = "getAccountWithOrders",
        attributeNodes = @NamedAttributeNode(value = "ordersList"))
public class Account {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;

    private String password;
    @Min(0)
    private Double balance;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orderCreator")
    @JsonBackReference
    private List<Order> ordersList;
}
