package com.bsuir.webpizza.models;

import javax.persistence.*;

@Entity
public class MyOrderPizza {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long Id;

    public Long PizzaId;
    public Long MyOrderId;
    public int Count;
}
