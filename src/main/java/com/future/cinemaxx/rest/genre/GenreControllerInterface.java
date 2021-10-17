package com.future.cinemaxx.rest.genre;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.GenreDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface GenreControllerInterface {
    @GetMapping
    List<GenreDTO> getAll();

    @GetMapping("/{id}")
    GenreDTO getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    GenreDTO create(@Valid @RequestBody GenreDTO genreDTO);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    GenreDTO update(@PathVariable("id") int id, @RequestBody GenreDTO genreDTO);
}