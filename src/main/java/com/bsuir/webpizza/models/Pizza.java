package com.bsuir.webpizza.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pizza {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    public Long Id;

    public String Name, Composition, Url;
    public double Price;
}
