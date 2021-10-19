package com.future.cinemaxx.rest.ticket;

import com.future.cinemaxx.dtos.TicketDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface TicketControllerInterface {
    @PutMapping("/book/{projectionId}/row/{row}/column/{column}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    TicketDTO bookTicket(@PathVariable("projectionId") int projectionId,@PathVariable("row") int row, @PathVariable("column") int column);

    @GetMapping
    List<TicketDTO> getAll();

    @GetMapping("/projection/{projectionId}/row/{row}/column/{column}")
    TicketDTO getTicketByProjectionRowAndColumn(@PathVariable("projectionId") int projectionId,@PathVariable("row") int row, @PathVariable("column") int column);

    @GetMapping("/projection/{projectionId}")
    List<TicketDTO> getTicketsByProjectionId(@PathVariable("projectionId") int projectionId);
}
