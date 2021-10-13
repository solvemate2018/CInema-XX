package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectionServiceImpl implements ProjectionServiceInterface {

    ProjectionRepo projectionRepo;
    MovieRepo movieRepo;
    CinemaHallRepo hallRepo;

    public ProjectionServiceImpl(ProjectionRepo projectionRepo, MovieRepo movieRepo,CinemaHallRepo hallRepo){
        this.movieRepo=movieRepo;
        this.projectionRepo=projectionRepo;
        this.hallRepo=hallRepo;
    }

    @Override
    public List<Projection> getAll() {
        return projectionRepo.findAll();
    }

    @Override
    public List<Projection> getByTheaterId(int id) {
        return projectionRepo.getProjectionsByHall_Theater_Id(id);
    }

    @Override
    public List<Projection> getByTheaterIdAndHallName(int theaterId, String hallName) {
        return projectionRepo.getProjectionsByHall_Theater_IdAndHall_Name(theaterId,hallName);
    }

    @Override
    public Projection getById(int id) {
        return projectionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice) {
        Movie movie = movieRepo.findById(movieId).orElseThrow(()-> new ResourceNotFoundException());
        CinemaHall hall = hallRepo.findById(hallId).orElseThrow(()->new ResourceNotFoundException());
        return projectionRepo.save(new Projection(startTime,ticketPrice,hall,movie));
    }

    @Override
    public void deleteProjection(int projectionId) {
        if(!projectionRepo.existsById(projectionId)) throw new ResourceNotFoundException();
        projectionRepo.deleteById(projectionId);
    }

    @Override
    public List<Projection> getByDateAndTheaterId(int theaterId, LocalDate time) {
        List<Projection> projections = projectionRepo.getProjectionsByStartTime(time);
        return projections.stream().filter
                (projection -> projection
                        .getHall().getTheater()
                        .getId()==theaterId).collect(Collectors.toList());
    }

    @Override
    public List<Projection> getProjectionsBetweenDates(int theaterId, LocalDate dateFrom, LocalDate dateTo) {
        return projectionRepo.getProjectionsByHall_Theater_IdAndStartTimeBetween(theaterId,dateFrom.atStartOfDay()
                ,dateTo.plusDays(1).atStartOfDay());
    }

}
