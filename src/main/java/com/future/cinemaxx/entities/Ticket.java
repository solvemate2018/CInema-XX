package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode @Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(nullable = false)
    private int row;

    @Basic
    @Column(nullable = false)
    private int column;

    @Basic
    @Column
    private boolean sold = false;

    @ManyToOne
    private Event event;

    public Ticket(int row, int column){
        this.row = row;
        this.column = column;
    }
}
