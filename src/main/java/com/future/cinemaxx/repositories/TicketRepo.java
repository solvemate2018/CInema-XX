package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
}
