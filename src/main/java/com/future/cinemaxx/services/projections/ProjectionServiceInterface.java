package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.Projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionServiceInterface {
    List<Projection> getAllProjections();
    List<Projection> getAllProjectionsByTheaterId(int id);
    Projection getProjectionById(int id);
    Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice);
    void deleteProjection(int projectionId);
    List<Projection> getAllProjectionsByDateAndTheaterId(LocalDate time, int theaterId);
    List<Projection> getAllProjectionsByDateAndTheaterId2(LocalDate time, int theaterId);
    List<Projection> getAllProjectionsBetweenDates(LocalDate date1, LocalDate date2);
}
