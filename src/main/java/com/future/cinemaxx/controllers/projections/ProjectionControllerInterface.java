package com.future.cinemaxx.controllers.projections;

import com.future.cinemaxx.dtos.ProjectionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface ProjectionControllerInterface {

    @GetMapping
    List<ProjectionDTO> getAll();

    @GetMapping("/{id}")
    ProjectionDTO getById(@PathVariable int id);

    @GetMapping("/theater/{theaterId}")
    List<ProjectionDTO> getByTheaterId(@PathVariable int theaterId);

    @GetMapping("/theater/{theaterId}/hall/{hallId}")
    List<ProjectionDTO> getByTheaterIdAndHall(@PathVariable int theaterId, @PathVariable int hallId);

    @GetMapping("/theater/{theaterId}/{time}")
    List<ProjectionDTO> getByDateAndTheaterId(@PathVariable int theaterId,@PathVariable LocalDate time);

    @GetMapping("/theater/{theaterId}/{date1}/{date2}")
    List<ProjectionDTO> getProjectionsBetweenDates(@PathVariable int theaterId, @PathVariable LocalDate date1, @PathVariable LocalDate date2);

}
