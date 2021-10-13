package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.*;
import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Theater;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOConverter {
    ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MovieDTO convertToMovieDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setCategory(modelMapper.map(movie.getCategory(), CategoryDTO.class));
        movieDTO.setGenre(modelMapper.map(movie.getGenre(), GenreDTO.class));
        return movieDTO;
    }

    public Movie convertToMovie(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, Movie.class);
    }

    public CinemaHallDTO convertToCinemaHallDTO(CinemaHall cinemaHall) {
        CinemaHallDTO cinemaHallDTO = modelMapper.map(cinemaHall, CinemaHallDTO.class);
        return cinemaHallDTO;

    }

    public CinemaHall convertToCinemaHall(CinemaHallDTO cinemaHallDTO) {
        return modelMapper.map(cinemaHallDTO, CinemaHall.class);
    }

    public TheaterDTO convertToTheaterDTO(Theater theater)
    {
        TheaterDTO theaterDTO = modelMapper.map(theater, TheaterDTO.class);
        return theaterDTO;
    }

    public Theater convertToTheater (TheaterDTO theaterDTO)
    {
        return modelMapper.map(theaterDTO, Theater.class);
    }


}
