package com.future.cinemaxx;

import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import com.future.cinemaxx.services.projections.ProjectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProjectionServiceTests {

    @Autowired
    MovieRepo movieRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProjectionRepo projectionRepo;
    @Autowired
    CinemaHallRepo cinemaHallRepo;


    MovieServiceImpl movieService;
    ProjectionServiceImpl projectionService;



    @BeforeEach
    public void initService(){
        movieService = new MovieServiceImpl(movieRepo,genreRepo,categoryRepo);
        projectionService = new ProjectionServiceImpl(projectionRepo,movieRepo,cinemaHallRepo,movieService);

    }

    @Test
    @Sql("/createMovies.sql")
    void getExpectedNumberOfProjections(){
        long count = projectionService.getAll().size();
        assertEquals(3,count);
    }

    @Test
    @Sql("/createMovies.sql")
    void updateProjection(){
        Projection projectionNew = projectionService.getById(100);
        projectionNew.setTicketPrice(99);
        projectionService.updateProjectionById(100,projectionNew);
        assertEquals(99,projectionService.getById(100).getTicketPrice());
    }

    @Test
    @Sql("/createMovies.sql")
    void updateProjectionMovieField(){
        Projection projectionNew = projectionService.getById(100);
        projectionNew.setMovie(movieRepo.findById(101).get());
        projectionService.updateProjectionById(100,projectionNew);
        assertEquals(movieRepo.findById(101).get(),projectionService.getById(100).getMovie());
    }

    @Test
    @Sql("/createMovies.sql")
    void updateProjectionNullField(){
        Projection projectionNew = projectionService.getById(100);
        projectionNew.setMovie(null);
        projectionService.updateProjectionById(100,projectionNew);
        assertEquals(null,projectionService.getById(100).getMovie());
    }

}
