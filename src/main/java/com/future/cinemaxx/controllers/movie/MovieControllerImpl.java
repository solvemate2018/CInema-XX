package com.future.cinemaxx.controllers.movie;

import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import com.future.cinemaxx.services.movies.MovieServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/movies")
public class MovieControllerImpl implements MovieControllerInterface {

    @Autowired
    MovieServiceImpl movieService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DTOConverter dtoConverter;

    @Autowired
    public MovieControllerImpl(MovieServiceImpl movieService, ModelMapper modelMapper)
    {
        this.movieService=movieService;
        this.modelMapper=modelMapper;
    }

    private MovieDTO convertToDto(Movie movie) {
        return modelMapper.map(movie, MovieDTO.class);
    }

    private Movie convertToEntity(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, Movie.class);
    }


    @Override
    public List<MovieDTO> getAll() {
        return null;
    }

    @Override
    public MovieDTO getById(int id) {
        return null;
    }

    @Override
    public void create(MovieDTO movieDTO) {
        movieService.createMovie(convertToEntity(movieDTO));
    }

}
