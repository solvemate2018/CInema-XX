package com.future.cinemaxx.rest.ticket;

import com.future.cinemaxx.dtos.TicketDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.tickets.TicketServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ticket")
public class TicketControllerImpl implements TicketControllerInterface {

    TicketServiceInterface ticketService;
    DTOConverter dtoConverter;

    @Autowired
    public TicketControllerImpl(TicketServiceInterface ticketService, DTOConverter dtoConverter) {
        this.ticketService = ticketService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public TicketDTO bookTicket(int projectionId, int row, int column) {
        return dtoConverter.convertToTicketDTO(ticketService.bookTicket(projectionId, row, column));
    }

    public List<TicketDTO> getAll() {
        return ticketService.getAllTickets().stream().map(ticket -> dtoConverter.convertToTicketDTO(ticket)).collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getByDateAndHallId(int hallId, LocalDateTime date) {
        return dtoConverter.convertToListTicketDTO(ticketService.getByDateAndHallId(hallId, date));

    }

    @Override
    public void delete(int id) {
    }

    public TicketDTO getTicketByProjectionRowAndColumn(int projectionId, int row, int column) {
        return dtoConverter.convertToTicketDTO(ticketService.getTicketByProjectionRowColumn(projectionId, row, column));
    }

    @Override
    public List<TicketDTO> getTicketsByProjectionId(int projectionId) {
        return ticketService.getTicketByProjection(projectionId).stream().map(ticket -> dtoConverter.convertToTicketDTO(ticket)).collect(Collectors.toList());
    }
}
