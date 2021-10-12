package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.Projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionServiceInterface {
    List<Projection> getAllProjections();
    Projection getProjectionById(int id);
    Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice);
    void deleteProjection(int projectionId);
    List<Projection> getAllProjectionsByDate(LocalDate time);
}
