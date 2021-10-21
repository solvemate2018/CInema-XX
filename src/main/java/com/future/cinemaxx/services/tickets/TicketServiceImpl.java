package com.future.cinemaxx.services.tickets;


import com.future.cinemaxx.entities.Ticket;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.repositories.TicketRepo;
import com.future.cinemaxx.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketServiceInterface {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    ProjectionRepo projectionRepo;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    @Override
    public Ticket getTicketById(long id) {
        return ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no such ticket in our system!"));
    }

    @Override
    public List<Ticket> getByDateAndHallId(int hallId, LocalDateTime date) {

        List<Ticket> tickets = ticketRepo.getTicketsByProjection_StartTimeAndProjectionHall_Id(date, hallId);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("There are no tickets in the cinemaHall " + hallId + " at " + date);
        }
        return tickets;
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteTicket(long id) {
        if (ticketRepo.existsById(id)) {
            ticketRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Ticket bookTicket(int projectionId, int row, int column) {
        Ticket ticketToBook = ticketRepo.getTicketByProjection_IdAndTicketRowAndTicketColumn(projectionId, row, column);
        if (ticketToBook == null) {
            throw new ResourceNotFoundException("There is no ticket for projection with id" + projectionId +
                    "located at row: " + row + " and column: " + column);
        }
        if (ticketToBook.isSold()) {
            throw new IllegalStateException("The selected ticket is already booked");
        }
        ticketToBook.setSold(true);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        ticketToBook.setUser(userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Invalid user")));
        return ticketRepo.save(ticketToBook);
    }

    @Override
    public Ticket cancelBooking(int projectionId, int row, int column) {
        Ticket ticketToBook = ticketRepo.getTicketByProjection_IdAndTicketRowAndTicketColumn(projectionId,row,column);
        if(ticketToBook==null){throw new ResourceNotFoundException("There is no ticket for projection with id" + projectionId+
                "located at row: "+row+" and column: "+column);}
        if(!ticketToBook.isSold()){throw new IllegalStateException("You haven't booked the selected ticket");}
        ticketToBook.setSold(false);
        return ticketRepo.save(ticketToBook);
    }

    @Override
    public Ticket getTicketByProjectionRowColumn(int projectionId, int row, int column) {
        Ticket ticket = ticketRepo.getTicketByProjection_IdAndTicketRowAndTicketColumn(projectionId, row, column);
        if (ticket == null) {
            throw new ResourceNotFoundException("There is no ticket for projection with id" + projectionId +
                    "located at row: " + row + " and column: " + column);
        }
        return ticket;
    }

    @Override
    public List<Ticket> getTicketByProjection(int projectionId) {
        List<Ticket> tickets = ticketRepo.getTicketsByProjection_id(projectionId);
        if (tickets.isEmpty()) {
            throw new ResourceNotFoundException("There are no tickets for projection with id: " + projectionId);
        }
        return tickets;
    }
}
