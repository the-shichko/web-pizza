package com.bsuir.webpizza.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pizza {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    public Long Id;

    public String Name, Composition, Url;
    public double Price;
}
