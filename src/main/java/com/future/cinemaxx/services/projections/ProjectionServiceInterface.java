package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.Projection;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionServiceInterface {
    List<Projection> getAll();
    List<Projection> getByTheaterId(int id);
    List<Projection> getByHallId(int hallId);
    List<Projection> getByTheaterIdAndHallName(int theaterId, String hallName);
    Projection getById(int id);
    Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice);
    void deleteProjection(int projectionId);
    Projection updateProjectionById(int id, Projection projection);
    List<Projection> getAllProjectionsBetweenDates(LocalDate date1, LocalDate date2);
    List<Projection> getByDateAndTheaterId(int theaterId, LocalDate date);
    List<Projection> getProjectionsBetweenDates(int theaterId, LocalDate timeFrom, LocalDate timeTo);

}
