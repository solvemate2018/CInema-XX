package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int ticketRow;

    @Column(nullable = false)
    private int ticketColumn;

    @Column
    private boolean sold = false;

    @ManyToOne
    Projection projection;

    public Ticket(int row, int column){
        this.ticketRow = row;
        this.ticketColumn = column;
    }
}
