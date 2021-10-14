package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectionServiceImpl implements ProjectionServiceInterface {

    ProjectionRepo projectionRepo;
    MovieRepo movieRepo;
    CinemaHallRepo hallRepo;

    public ProjectionServiceImpl(ProjectionRepo projectionRepo, MovieRepo movieRepo, CinemaHallRepo hallRepo) {
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.hallRepo = hallRepo;
    }

    @Override
    public List<Projection> getAll() {
        return projectionRepo.findAll();
    }

    @Override
    public List<Projection> getByTheaterId(int id) {
        List<Projection> projections = projectionRepo.getProjectionsByHall_Theater_Id(id);
        if(projections.isEmpty()){throw new ResourceNotFoundException("There are no projections in a theater with id: " +id);}
        return projections;
    }

    @Override
    public List<Projection> getByHallId(int hallId) {
        List<Projection> projections = projectionRepo.getProjectionByHall_Id(hallId);
        if(projections.isEmpty()){throw new ResourceNotFoundException("There are no projections in a hall with id: "+hallId);}
        return projections;
    }

    @Override
    public List<Projection> getByTheaterIdAndHallName(int theaterId, String hallName) {
        List<Projection> projections = projectionRepo.getProjectionsByHall_Theater_IdAndHall_Name(theaterId, hallName);
        if(projections.isEmpty()){throw new ResourceNotFoundException("There are no projections in a theater with id: "
                +theaterId +" and in the hall " + hallName);}
        return projections;
    }

    @Override
    public Projection getById(int id) {
        return projectionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Projection with id:" + id));
    }

    @Override
    public Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice) {
        Movie movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("There is no movie with id: " + movieId));
        CinemaHall hall = hallRepo.findById(hallId).orElseThrow(() -> new ResourceNotFoundException("There is no cinema hall with id: " + movieId));
        return projectionRepo.save(new Projection(startTime, ticketPrice, hall, movie));
    }

    @Override
    public void deleteProjection(int projectionId) {
        if (!projectionRepo.existsById(projectionId)) throw new ResourceNotFoundException();
        projectionRepo.deleteById(projectionId);
    }

    @Override
    public List<Projection> getByDateAndTheaterId(int theaterId, LocalDate date) {

        List<Projection> projections = projectionRepo
                .getProjectionsByHall_Theater_IdAndStartTimeBetween(theaterId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        if (projections.isEmpty()) {
            throw new ResourceNotFoundException("There are no projection in the cinema with id: " +
                    theaterId + " on " + date);
        }
        return projections;
    }

    @Override
    public List<Projection> getProjectionsBetweenDates(int theaterId, LocalDate dateFrom, LocalDate dateTo) {

        List<Projection> projections = projectionRepo.
                getProjectionsByHall_Theater_IdAndStartTimeBetween(theaterId, dateFrom.atStartOfDay()
                        , dateTo.plusDays(1).atStartOfDay());
        if (projections.isEmpty()) {
            throw new ResourceNotFoundException("There are no projections in the theater with id: " +
                    theaterId + " between " + dateFrom + " and " + dateTo);
        }
        return projections;
    }
}
