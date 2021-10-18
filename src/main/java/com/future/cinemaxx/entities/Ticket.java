package com.future.cinemaxx.entities;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int ticketRow;

    @Column(nullable = false)
    private int ticketColumn;

    @Column
    private boolean sold = false;

    @ManyToOne
    Projection projection;

    public Ticket(int row, int column, boolean sold, Projection projection){
        this.ticketRow = row;
        this.ticketColumn = column;
        this.sold=sold;
        this.projection=projection;
    }

    public Ticket(int row, int column, Projection projection){
        this.ticketRow= row;
        this.ticketColumn = column;
        this.projection = projection;
    }
}
