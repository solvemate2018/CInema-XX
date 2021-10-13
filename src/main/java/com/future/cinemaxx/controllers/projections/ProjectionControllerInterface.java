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

    @GetMapping("/theater/{theaterId}/hall/{hallName}")
    List<ProjectionDTO> getByTheaterIdAndHallName(@PathVariable int theaterId, @PathVariable String hallName);

    @GetMapping("/theater/{theaterId}/{date}")
    List<ProjectionDTO> getByDateAndTheaterId(@PathVariable int theaterId,@PathVariable String date);

    @GetMapping("/theater/{theaterId}/{dateFrom}/{dateTo}")
    List<ProjectionDTO> getProjectionsBetweenDates(@PathVariable int theaterId, @PathVariable String dateFrom, @PathVariable String dateTo);

}
