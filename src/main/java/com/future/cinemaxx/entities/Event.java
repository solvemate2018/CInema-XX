package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Getter @Setter
@EqualsAndHashCode @Table(name = "events")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="event_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Basic
    @Column(nullable = false)
    private Duration duration;

    @Basic
    @Column(nullable = false)
    private float ticketPrice;

    @ManyToOne
    private CinemaHall hall;

    @OneToMany(mappedBy = "event")
    private Collection<Ticket> tickets;

    public Event(){
        tickets = new ArrayList<>();
        for(int i = 1; i <= hall.getRows(); i++){
            for(int j = 1; j <= hall.getColumns(); j++){
                tickets.add(new Ticket(i, j));
            }
        }
    }
}
