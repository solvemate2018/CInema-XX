package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Projection> getAllProjections() {
        return projectionRepo.findAll();
    }

    @Override
    public Projection getProjectionById(int id) {
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
    public List<Projection> getAllProjectionsByDate(LocalDate time) {
        List<Projection> projections = getAllProjections();
        return projections.stream().filter(p -> p.getStartTime()
                .toLocalDate().equals(time)).collect(Collectors.toList());
    }
}
