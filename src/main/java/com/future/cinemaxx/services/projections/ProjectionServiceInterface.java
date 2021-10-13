package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.Projection;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionServiceInterface {
    List<Projection> getAll();
    List<Projection> getByTheaterId(int id);
    List<Projection> getByTheaterIdAndHall(int theaterId, int hallDd);
    Projection getById(int id);
    Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice);
    void deleteProjection(int projectionId);
    List<Projection> getByDateAndTheaterId(int theaterId, LocalDate time);
    List<Projection> getProjectionsBetweenDates(int theaterId, LocalDate date1, LocalDate date2);
}
