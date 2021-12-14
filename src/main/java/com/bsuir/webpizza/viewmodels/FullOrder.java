package com.bsuir.webpizza.viewmodels;

import com.bsuir.webpizza.models.Pizza;

import java.util.List;

public class FullOrder {
    public Long Id;
    public String Number;
    public double Price;
    public String Status;
    public List<Pizza> Items;

    public FullOrder(Long id, String number, double price, String status){
        Id = id;
        Number = number;
        Price = price;
        Status = status;
    }
}
