package com.spring.e_commerce.model;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}
}
