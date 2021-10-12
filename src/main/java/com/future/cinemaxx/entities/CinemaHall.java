package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Getter @Setter
@NoArgsConstructor
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Usually something like "A", "B" etc.
    @Column(length = 10, nullable = false)
    private String name;

    @Column
    private int rows;

    @Column
    private int columns;

    @ManyToOne
    Theater theater;

    @OneToMany(mappedBy = "hall")
    Collection<Projection> projections = new ArrayList<>();

    public CinemaHall(String name, int rows, int columns, Theater theater){
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.theater = theater;
    }
}
