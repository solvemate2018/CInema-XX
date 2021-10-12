package com.future.cinemaxx.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Something like A B C...
    @Column(length = 4, unique = true)
    private String name;

    @Column
    private int ageLimit;

    public Category(String name, int ageLimit){
        this.name = name;
        this.ageLimit = ageLimit;
    }
}
