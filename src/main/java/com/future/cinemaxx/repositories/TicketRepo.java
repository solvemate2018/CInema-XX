package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
    void deleteAllByProjectionId(int id);

    Ticket getTicketByProjection_IdAndTicketRowAndTicketColumn(int projectionId, int rows, int column);

    List<Ticket> getTicketsByProjection_id(int projectionId);

    List<Ticket> getTicketsByProjection_StartTimeAndProjectionHall_Id(LocalDateTime startTime, int id);

}
