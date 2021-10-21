package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.repositories.TicketRepo;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectionServiceImpl implements ProjectionServiceInterface {

    @Autowired
    DTOConverter dtoConverter;

    MovieServiceImpl movieService;
    ProjectionRepo projectionRepo;
    MovieRepo movieRepo;
    CinemaHallRepo hallRepo;
    TicketRepo ticketRepo;


    public ProjectionServiceImpl(ProjectionRepo projectionRepo, MovieRepo movieRepo, CinemaHallRepo hallRepo, MovieServiceImpl movieService, TicketRepo ticketRepo) {
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.hallRepo = hallRepo;
        this.movieService = movieService;
        this.ticketRepo = ticketRepo;
    }

    @Override
    public List<Projection> getAll() {
        return projectionRepo.findAll();
    }

    @Override
    public List<Projection> getByTheaterId(int id) {
        List<Projection> projections = projectionRepo.getProjectionsByHall_Theater_Id(id);
        if (projections.isEmpty()) {
            throw new ResourceNotFoundException("There are no projections in a theater with id: " + id);
        }
        return projections;
    }

    @Override
    public List<Projection> getByHallId(int hallId) {
        List<Projection> projections = projectionRepo.getProjectionByHall_Id(hallId);
        if (projections.isEmpty()) {
            throw new ResourceNotFoundException("There are no projections in a hall with id: " + hallId);
        }
        return projections;
    }

    @Override
    public List<Projection> getByTheaterIdAndHallName(int theaterId, String hallName) {
        List<Projection> projections = projectionRepo.getProjectionsByHall_Theater_IdAndHall_Name(theaterId, hallName);
        if (projections.isEmpty()) {
            throw new ResourceNotFoundException("There are no projections in a theater with id: "
                    + theaterId + " and in the hall " + hallName);
        }
        return projections;
    }

    @Override
    public Projection getById(int id) {
        return projectionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Projection with id:" + id));
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

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Projection createProjection(Projection projection, int movieId, int hallId) {
        if(projection.getStartTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("The Date/Time of the projection should be after now!");
        }
        if(projection.getTicketPrice() < 0){
            throw new IllegalArgumentException("The price of the ticket cannot be lower than 0");
        }
        Projection newProjection = new Projection(projection.getStartTime(),
                projection.getTicketPrice(),
                hallRepo.findById(hallId).orElseThrow(() -> new ResourceNotFoundException("Cinema hall with this id does not exist:" + movieId)),
                movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie  with this id does not exist:" + hallId)));
        Projection result = projectionRepo.save(newProjection);
        ticketRepo.saveAll(newProjection.getTickets());
        return result;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void deleteProjection(int projectionId) {
        if (!projectionRepo.existsById(projectionId)) {
            throw new ResourceNotFoundException("There is no such projection in our system");
        }
        ticketRepo.deleteAllByProjectionId(projectionId);
        projectionRepo.deleteById(projectionId);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Projection updateProjectionById(int id, int movieId, int hallId, Projection projection) {
        Projection updatedProjection = projectionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no cinema hall with id " + id));

        if (projection.getStartTime() != null) {
            updatedProjection.setStartTime(projection.getStartTime());
        }
        if (projection.getTicketPrice() != 0) {
            updatedProjection.setTicketPrice(projection.getTicketPrice());
        }
        if(projection.getStartTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("The Date/Time of the projection should be after now!");
        }
        if(projection.getTicketPrice() < 0){
            throw new IllegalArgumentException("The price of the ticket cannot be lower than 0");
        }
        if (hallId != 0) {
            CinemaHall cinemaHall = hallRepo.findById(hallId)
                    .orElseThrow(() -> new ResourceNotFoundException("There is no cinema hall with id" + hallId));
            updatedProjection.setHall(cinemaHall);
        }
        if (movieId != 0) {
            Movie movie = movieRepo.findById(movieId)
                    .orElseThrow(() -> new ResourceNotFoundException("There is no movie with id" + movieId));
                 updatedProjection.setMovie(movie);
        }
        return projectionRepo.save(updatedProjection);
    }
}