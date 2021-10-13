package com.future.cinemaxx.rest.projection;

import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.dtos.ProjectionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface ProjectionControllerInterface {

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProjectionById(@PathVariable("id") int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateProjection( @PathVariable("id") int id, @RequestBody ProjectionDTO projectionDTO);
}
