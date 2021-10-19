package com.future.cinemaxx.rest.ticket;

import com.future.cinemaxx.dtos.TicketDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketControllerInterface {

    @PutMapping("/book/{projectionId}/row/{row}/column/{column}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    TicketDTO bookTicket(@PathVariable("projectionId") int projectionId, @PathVariable("row") int row, @PathVariable("column") int column);

    @GetMapping
    List<TicketDTO> getAll();

    @GetMapping("/projection/{projectionId}/row/{row}/column/{column}")
    TicketDTO getTicketByProjectionRowAndColumn(@PathVariable("projectionId") int projectionId, @PathVariable("row") int row, @PathVariable("column") int column);

    @GetMapping("/projection/{projectionId}")
    List<TicketDTO> getTicketsByProjectionId(@PathVariable("projectionId") int projectionId);

    @GetMapping("/hall/{hallId}/getByDate")
    List<TicketDTO> getByDateAndHallId(@PathVariable int hallId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
