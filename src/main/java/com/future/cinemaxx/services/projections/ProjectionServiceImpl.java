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
    public List<Projection> getAllProjections() {
        return projectionRepo.findAll();
    }

    @Override
    public List<Projection> getAllProjectionsByTheaterId(int id) {
        return projectionRepo.getProjectionByHall_Theater_Id(id);
    }

    @Override
    public Projection getProjectionById(int id) {
        return projectionRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Projection createProjection(int movieId, int hallId, LocalDateTime startTime, float ticketPrice) {
        Movie movie = movieRepo.findById(movieId).orElseThrow(ResourceNotFoundException::new);
        CinemaHall hall = hallRepo.findById(hallId).orElseThrow(ResourceNotFoundException::new);
        return projectionRepo.save(new Projection(startTime,ticketPrice,hall,movie));
    }

    @Override
    public void deleteProjection(int projectionId) {
        if(!projectionRepo.existsById(projectionId)) throw new ResourceNotFoundException();
        projectionRepo.deleteById(projectionId);
    }

    @Override
    //Might not be needed
    public List<Projection> getAllProjectionsByDateAndTheaterId(LocalDate time,int id) {
        List<Projection> projections = getAllProjections();
        return projections.stream().filter(p -> p.getStartTime()
                .toLocalDate().equals(time)&& p.getHall().getTheater().getId()==id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Projection> getAllProjectionsByDateAndTheaterId2(LocalDate time, int theaterId) {
        return projectionRepo.getProjectionByHall_Theater_IdAndStartTime(theaterId,time);
    }

    @Override
    public List<Projection> getAllProjectionsBetweenDates(LocalDate date1, LocalDate date2) {
        //Temporary solution
        return projectionRepo.getProjectionsByStartTimeBetween(date1.atStartOfDay(),date2.atStartOfDay());
    }

}
