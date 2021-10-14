package com.future.cinemaxx.services.projections;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import com.future.cinemaxx.services.movies.movieServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectionServiceImpl implements ProjectionServiceInterface {


    MovieServiceImpl movieService;
    ProjectionRepo projectionRepo;
    MovieRepo movieRepo;
    CinemaHallRepo hallRepo;

    public ProjectionServiceImpl(ProjectionRepo projectionRepo, MovieRepo movieRepo,CinemaHallRepo hallRepo, MovieServiceImpl movieService){
        this.movieRepo=movieRepo;
        this.projectionRepo=projectionRepo;
        this.hallRepo=hallRepo;
        this.movieService=movieService;
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
    public Projection updateProjectionById(int id, Projection projection) {
        Projection updatedProjection = projectionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        if(projection.getStartTime()!=null){ updatedProjection.setStartTime(projection.getStartTime()); }
        if(projection.getTicketPrice()!=0){updatedProjection.setTicketPrice(projection.getTicketPrice()); }
        if(projection.getHall()!=null){
            CinemaHall cinemaHall = hallRepo.findCinemaHallByName(projection.getHall().getName());
            if(cinemaHall==null){
                throw new ResourceNotFoundException();
            }else{
                updatedProjection.setHall(cinemaHall);
            }
        }
        if(projection.getMovie()!=null){
            movieService.updateMovie(projection.getMovie().getId(), projection.getMovie());
        }

        return updatedProjection;
    }

    @Override
    public List<Projection> getAllProjectionsBetweenDates(LocalDate date1, LocalDate date2) {
        return projectionRepo.getProjectionsByStartTimeBetween(date1.atStartOfDay(),date2.atStartOfDay());
    }

}
