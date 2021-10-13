package com.future.cinemaxx.services.tickets;

import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Ticket;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public class TicketServiceImpl implements TicketServiceInterface{
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    ProjectionRepo projectionRepo;

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    @Override
    public Ticket getTicketById(int id) {
        return ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Ticket createTicket(Ticket ticket, int projectionId) {
        return ticketRepo.save(new Ticket(ticket.getTicketRow(),
                ticket.getTicketColumn(),
                ticket.isSold(),
                projectionRepo.findById(projectionId).orElseThrow(() -> new ResourceNotFoundException())));
    }

    @Override
    public void deleteTicket(int id) {
    if (ticketRepo.existsById(id)){
        ticketRepo.deleteById(id);
    }else{
        throw new ResourceNotFoundException();
    }
    }
}
