package com.future.cinemaxx.services.tickets;

import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.entities.Ticket;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<Ticket> getByDateAndHallId(int hallId, LocalDateTime date) {

        List<Ticket> tickets = ticketRepo.getTicketsByProjection_StartTimeAndProjectionHall_Id(date, hallId);
        if (tickets.isEmpty())
        {
            throw new ResourceNotFoundException("There are no tickets in the cinemaHall " + hallId + " at " + date);
        }
        return tickets;
    }


    @Override
    public void deleteTicket(long id) {
    if (ticketRepo.existsById(id)){
        ticketRepo.deleteById(id);
    }else{
        throw new ResourceNotFoundException();
    }
    }
    @Override
    public Ticket bookTicket(int projectionId, int row, int column) {
        Ticket ticketToBook = ticketRepo.getTicketByProjection_IdAndTicketRowAndTicketColumn(projectionId,row,column);
        if(ticketToBook==null){throw new ResourceNotFoundException("There is no ticket for projection with id" + projectionId+
                "located at row: "+row+" and column: "+column);}
        if(ticketToBook.isSold()){throw new IllegalStateException("The selected ticket is already booked");}
        ticketToBook.setSold(true);
        return ticketRepo.save(ticketToBook);
    }

    @Override
    public Ticket getTicketByProjectionRowColumn(int projectionId, int row, int column) {
        Ticket ticket = ticketRepo.getTicketByProjection_IdAndTicketRowAndTicketColumn(projectionId,row,column);
        if(ticket==null){throw new ResourceNotFoundException("There is no ticket for projection with id" + projectionId+
                "located at row: "+row+" and column: "+column);}
        return ticket;
    }

    @Override
    public List<Ticket> getTicketByProjection(int projectionId) {
        List<Ticket> tickets = ticketRepo.getTicketsByProjection_id(projectionId);
        if(tickets.isEmpty()){throw new ResourceNotFoundException("There are no tickets for projection with id: "+projectionId);}
        return tickets;
    }
}
