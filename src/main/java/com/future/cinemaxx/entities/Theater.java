package com.future.cinemaxx.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Collection;

@Entity @Getter @Setter
@EqualsAndHashCode @Table(name = "theaters")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(unique = true, length = 40, nullable = false)
    private String name;

    @Basic
    @Column(length = 40, nullable = false)
    private String city;

    @Basic
    @Column(length = 40, nullable = false)
    private String street;

    @Basic
    @Column(nullable = false)
    private int streetNumber;

    @OneToMany(mappedBy = "theater")
    private Collection<CinemaHall> cinemaHalls;
}
