package com.future.cinemaxx.controllers.projections;

import com.future.cinemaxx.dtos.ProjectionDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ProjectionControllerInterface {

    @GetMapping
    List<ProjectionDTO> getAll();

    @GetMapping("/{id}")
    ProjectionDTO getById(@PathVariable int id);

    @GetMapping("/theater/{theaterId}")
    List<ProjectionDTO> getByTheaterId(@PathVariable int theaterId);

    @GetMapping("/hall/{hallId}")
    List<ProjectionDTO> getByHallId(@PathVariable int hallId);

    @GetMapping("/theater/{theaterId}/hall/{hallName}")
    List<ProjectionDTO> getByTheaterIdAndHallName(@PathVariable int theaterId, @PathVariable String hallName);

    @GetMapping("/theater/{theaterId}/getByDate")
    List<ProjectionDTO> getByDateAndTheaterId(@PathVariable int theaterId,@RequestParam("date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date);

    @GetMapping("/theater/{theaterId}/getByTwoDates")
    List<ProjectionDTO> getProjectionsBetweenDates(@PathVariable int theaterId, @RequestParam("dateFrom") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate dateFrom,
                                                   @RequestParam("dateTo") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateTo);

}
