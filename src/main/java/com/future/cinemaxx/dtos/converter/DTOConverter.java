package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.GenreDTO;
import com.future.cinemaxx.dtos.MovieDTO;
import com.future.cinemaxx.entities.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOConverter {
    ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public MovieDTO convertToMovieDTO(Movie movie){
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setCategory(modelMapper.map(movie.getCategory(), CategoryDTO.class));
        movieDTO.setGenre(modelMapper.map(movie.getGenre(), GenreDTO.class));
        return movieDTO;
    }

    public Movie convertToMovie(MovieDTO movieDTO){
        return modelMapper.map(movieDTO, Movie.class);
    }
}
