package com.future.cinemaxx.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.convert.DurationFormat;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Getter @Setter
@NoArgsConstructor
public class Movie {

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

    @OneToMany(mappedBy = "movie")
    Collection<Projection> projections = new ArrayList<>();

    public Movie(String name, Duration duration, Genre genre, Category category){
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.category = category;
    }
}
