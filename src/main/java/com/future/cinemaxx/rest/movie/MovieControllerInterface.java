package com.future.cinemaxx.rest.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.dtos.MovieDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface MovieControllerInterface {
    @GetMapping
    List<MovieDTO> getAll();

    @GetMapping("/{id}")
    MovieDTO getById(@PathVariable int id);

    @PostMapping("/genre/{genreId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    MovieDTO create(@RequestBody MovieDTO movie, @PathVariable int genreId, @PathVariable int categoryId);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMovieById(@PathVariable("id") int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    MovieDTO update(@PathVariable("id") int id, @RequestBody MovieDTO movieDTO);

    @GetMapping("/details/{movieId}")
    MovieDetails getDetails(@PathVariable int movieId) throws JsonProcessingException;
}
