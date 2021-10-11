package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity @Getter @Setter
@EqualsAndHashCode @Table(name = "cinemaHalls")
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Usually something like "A", "B" etc.
    @Basic
    @Column(length = 10, nullable = false)
    private String name;

    @Basic
    @Column(length = 10)
    private int rows;

    @Basic
    @Column(length = 10)
    private int columns;

    @ManyToOne
    private Theater theater;

    @OneToMany(mappedBy = "hall")
    private Collection<Event> events;
}
