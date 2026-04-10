package com.spring.e_commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    public Long getId(){return id;}
    public void setId(){this.id=id;}

    public String getName(){return name;}
    public void setName(){this.name=name;}

    public double getPrice(){return price;}
    public void setPrice(){this.price=price;}
}
