package com.bsuir.webpizza.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MyOrder {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    public Long Id;

    public String Name;
    public double Price;
    public String Status;
}
