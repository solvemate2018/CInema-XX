package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    List<Ticket> getTicketsByProjection_StartTimeAndProjectionHall_Id(LocalDateTime startTime, int id);

}
