package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
    void deleteByProjection_Id(int id);
    Ticket getTicketByProjection_IdAndTicketRowAndTicketColumn(int projectionId, int rows, int column);
    List<Ticket> getTicketsByProjection_id(int projectionId);
}
