package com.future.cinemaxx.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Projection {
    @ManyToOne
    CinemaHall hall;
    @OneToMany(mappedBy = "projection")
    Collection<Ticket> tickets = new ArrayList<>();
    @ManyToOne
    Movie movie;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private float ticketPrice;

    public Projection(LocalDateTime startTime, float ticketPrice, CinemaHall hall, Movie movie) {
        this.startTime = startTime;
        this.ticketPrice = ticketPrice;
        this.hall = hall;
        this.movie = movie;

        for (int i = 1; i <= hall.getNumberOfRows(); i++) {
            for (int j = 1; j <= hall.getNumberOfColumns(); j++) {
                tickets.add(new Ticket(i, j, this));
            }
        }
    }
}
