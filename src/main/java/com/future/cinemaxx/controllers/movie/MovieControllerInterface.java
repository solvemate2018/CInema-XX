package com.future.cinemaxx.controllers.movie;

import com.future.cinemaxx.dtos.MovieDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface MovieControllerInterface {

    @GetMapping
    List<MovieDTO> getAll();

    @GetMapping("/{id}")
    MovieDTO getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody MovieDTO movie);


}
