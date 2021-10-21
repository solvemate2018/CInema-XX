package com.future.cinemaxx.rest.cinemaHall;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.CinemaHallDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CinemaHallControllerInterface {
    @GetMapping
    List<CinemaHallDTO> getAll();

    @GetMapping("/{id}")
    CinemaHallDTO getById(@PathVariable int id);

    @PostMapping("/theater/{theaterId}")
    @ResponseStatus(HttpStatus.CREATED)
    CinemaHallDTO create(@RequestBody CinemaHallDTO cinemaHallDTO, @PathVariable int theaterId);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") int id);
}
