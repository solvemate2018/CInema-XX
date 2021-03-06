package com.future.cinemaxx.services.tickets;

import com.future.cinemaxx.dtos.TicketDTO;
import com.future.cinemaxx.entities.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketServiceInterface {
    List<Ticket> getAllTickets();
    Ticket getTicketById(long id);
    List <Ticket> getByDateAndHallId (int hallId, LocalDateTime date);
    void deleteTicket(long id);
    Ticket bookTicket(int projectionId, int row, int column);
    Ticket cancelBooking(int projectionId, int row, int column);
    Ticket getTicketByProjectionRowColumn(int projectionId, int row, int column);
    List<Ticket> getTicketByProjection(int projectionId);
}
