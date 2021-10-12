package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.*;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.entities.Ticket;
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

    public TheaterDTO convertToTheaterDTO(Theater theater){
        TheaterDTO theaterDTO = modelMapper.map(theater, TheaterDTO.class);
        //modelMapper.map(theater.getCinemaHalls(), theaterDTO.getCinemaHallDTOs());
        return theaterDTO;
    }

    public Theater convertToTheater(TheaterDTO theaterDTO){
        return modelMapper.map(theaterDTO, Theater.class);
    }

    public TicketDTO convertToTicketDTO(Ticket ticket){
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        ticketDTO.setProjection(modelMapper.map(ticket.getProjection(),ProjectionDTO.class));
        return ticketDTO;
    }

    public Ticket convertToTicket(TicketDTO ticketDTO){
        return modelMapper.map(ticketDTO, Ticket.class);
    }
}
