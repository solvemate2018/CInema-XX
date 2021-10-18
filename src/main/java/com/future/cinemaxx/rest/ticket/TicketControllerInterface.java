package com.future.cinemaxx.rest.ticket;

import com.future.cinemaxx.dtos.TicketDTO;
import com.future.cinemaxx.entities.Ticket;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketControllerInterface {

    @GetMapping
    List<TicketDTO> getAll();

    @GetMapping("/hall/{hallId}/getByDate")
    List<TicketDTO> getByDateAndHallId(@PathVariable int hallId, @RequestParam("date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDateTime date);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
