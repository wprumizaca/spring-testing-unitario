package com.example.persistence.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter @ToString
@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String title;

    private String description;


    public Book() {
    }

    public Book(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
