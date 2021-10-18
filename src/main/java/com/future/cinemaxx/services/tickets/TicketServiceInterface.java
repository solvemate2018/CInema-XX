package com.future.cinemaxx.services.tickets;

import com.future.cinemaxx.dtos.TicketDTO;
import com.future.cinemaxx.entities.Ticket;

import java.util.List;

public interface TicketServiceInterface {
    List<Ticket> getAllTickets();
    Ticket getTicketById(long id);
    Ticket createTicket(Ticket ticket, int projectionId);
    void deleteTicket(long id);
}
