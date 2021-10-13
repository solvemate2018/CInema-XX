package com.future.cinemaxx.rest.movie;

import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import com.future.cinemaxx.services.movies.movieServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieControllerImpl implements MovieControllerInterface{

    @Autowired
    MovieServiceImpl movieService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DTOConverter dtoConverter;

    @Override
    public void deleteMovieById(int id) {
    movieService.deleteMovie(id);
    }

    @Override
    public void update(int id, MovieDTO movieDTO) {
    movieService.updateMovie(id,dtoConverter.convertToMovie(movieDTO));
    }
}
