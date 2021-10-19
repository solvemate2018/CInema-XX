package com.future.cinemaxx.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @OneToMany(mappedBy = "movie")
    Collection<Projection> projections = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 40, unique = true)
    private String name;
    @Column
    private Duration duration;
    @OneToOne
    private Genre genre;
    @OneToOne
    private Category category;

    public Movie(String name, Duration duration, Genre genre, Category category) {
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.category = category;
    }
}
