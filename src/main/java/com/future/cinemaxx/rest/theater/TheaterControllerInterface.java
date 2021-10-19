package com.future.cinemaxx.rest.theater;

import com.future.cinemaxx.dtos.GenreDTO;
import com.future.cinemaxx.dtos.TheaterDTO;
import com.future.cinemaxx.entities.Theater;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface TheaterControllerInterface {
    @GetMapping
    List<TheaterDTO> getAll();

    @GetMapping("/{id}")
    TheaterDTO getTheaterById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TheaterDTO createTheater(@Valid @RequestBody TheaterDTO theaterDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    TheaterDTO updateTheater(@PathVariable("id") int id, @RequestBody TheaterDTO theater);

    @PutMapping("/theater/{theaterId}/hall/{hallId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    TheaterDTO addCinemaHall(@PathVariable("theaterId")int projectionId,@PathVariable("hallId") int hallId);
}
