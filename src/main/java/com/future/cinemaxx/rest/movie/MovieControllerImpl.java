package com.future.cinemaxx.rest.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/movie")
public class MovieControllerImpl implements MovieControllerInterface {
    @Autowired
    MovieServiceImpl movieService;
    @Autowired
    DTOConverter dtoConverter;

    public MovieControllerImpl(MovieServiceImpl movieService, DTOConverter dtoConverter) {
        this.movieService = movieService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<MovieDTO> getAll() {
        return movieService.getAllMovies().stream().map(movie -> dtoConverter.convertToMovieDTO(movie)).collect(Collectors.toList());
    }

    @Override
    public MovieDTO getById(int id) {
        return dtoConverter.convertToMovieDTO(movieService.getMovieById(id));
    }

    @Override
    public MovieDTO create(MovieDTO movieDTO, int categoryId, int genreId) {
        return dtoConverter.convertToMovieDTO(movieService.createMovie(dtoConverter.convertToMovie(movieDTO), genreId, categoryId));
    }

    @Override
    public void deleteMovieById(int id) {
        movieService.deleteMovie(id);
    }

    @Override
    public MovieDTO update(int id, MovieDTO movieDTO) {
        return dtoConverter.convertToMovieDTO(movieService.updateMovie(id, dtoConverter.convertToMovie(movieDTO)));
    }

    @Override
    public MovieDetails getDetails(int movieId) throws JsonProcessingException {
        return movieService.getMovieDetails(movieId);
    }
}
