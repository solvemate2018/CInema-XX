package com.future.cinemaxx.entities;

import com.future.cinemaxx.security.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
    @ManyToOne
    Projection projection;
    @ManyToOne
    User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int ticketRow;
    @Column(nullable = false)
    private int ticketColumn;
    @Column
    private boolean sold = false;

    public Ticket(int row, int column, boolean sold, Projection projection) {
        this.ticketRow = row;
        this.ticketColumn = column;
        this.sold = sold;
        this.projection = projection;
    }

    public Ticket(int row, int column, Projection projection) {
        this.ticketRow = row;
        this.ticketColumn = column;
        this.projection = projection;
    }
}
