package com.future.cinemaxx.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity @Getter @Setter
@EqualsAndHashCode
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(length = 40, unique = true)
    private String name;

    @OneToOne
    private Genre genre;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "movie")
    private Collection<Projection> projections;
}
