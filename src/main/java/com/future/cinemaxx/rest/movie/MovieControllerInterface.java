package com.future.cinemaxx.rest.movie;

import com.future.cinemaxx.dtos.MovieDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface MovieControllerInterface {

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMovieById(@PathVariable("id") int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update( @PathVariable("id") int id, @RequestBody MovieDTO movieDTO);

}
