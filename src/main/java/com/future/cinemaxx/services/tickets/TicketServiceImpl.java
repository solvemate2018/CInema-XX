package com.future.cinemaxx.services.tickets;

import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Ticket;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Ticket getTicketById(long id) {
        return ticketRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Ticket createTicket(Ticket ticket, int projectionId) {
        return ticketRepo.save(new Ticket(ticket.getTicketRow(),
                ticket.getTicketColumn(),
                ticket.isSold(),
                projectionRepo.findById(projectionId).orElseThrow(ResourceNotFoundException::new)));
    }

    @Override
    public void deleteTicket(long id) {
    if (ticketRepo.existsById(id)){
        ticketRepo.deleteById(id);
    }else{
        throw new ResourceNotFoundException();
    }
    }
}
