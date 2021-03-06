package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.dtos.ProjectionDTO;
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
    Projection createProjection(Projection projection, int movieID, int hallID);
    void deleteProjection(int projectionId);
    Projection updateProjectionById(int id, int movieId, int hallId, Projection projection);
    List<Projection> getByDateAndTheaterId(int theaterId, LocalDate date);
    List<Projection> getProjectionsBetweenDates(int theaterId, LocalDate timeFrom, LocalDate timeTo);

}
