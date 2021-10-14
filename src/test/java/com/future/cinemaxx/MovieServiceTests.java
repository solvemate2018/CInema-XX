package com.future.cinemaxx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class MovieServiceTests {

    @Autowired
    MovieRepo movieRepo;
    @Autowired
    GenreRepo genreRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProjectionRepo projectionRepo;
    MovieServiceImpl movieService;
    ModelMapper modelMapper;

    @BeforeEach
    public void initService(){
        movieService = new MovieServiceImpl(movieRepo,genreRepo,categoryRepo, projectionRepo, new ObjectMapper());
        modelMapper = new ModelMapper();
    }


    @Test
    @Sql("/createMovies.sql")
    void getExpectedNumberOfMovies(){
        long count = movieService.getAllMovies().size();
        assertEquals(3,count);
    }

    @Test
    @Sql("/createMovies.sql")
    void deleteMovie(){
        long before = movieService.getAllMovies().size();
        movieService.deleteMovie(100);
        long after = movieService.getAllMovies().size();
        assertEquals(2,after);
    }

    @Test
    @Sql("/createMovies.sql")
    void updateMovie(){
        Movie movieNew = movieService.getMovieById(100);
        movieNew.setName("new name");
        movieService.updateMovie(100, movieNew);
        assertEquals("new name", movieService.getMovieById(100).getName());
    }

    @Test
    @Sql("/createMovies.sql")
    void updateMovieWithNullField(){
        MovieDTO movieNew = modelMapper.map(movieService.getMovieById(100), MovieDTO.class);
        String oldName = movieNew.getName();
        movieNew.setName(null);
        movieService.updateMovie(100, modelMapper.map(movieNew, Movie.class));
        assertEquals(oldName, movieService.getMovieById(100).getName());
    }

}
