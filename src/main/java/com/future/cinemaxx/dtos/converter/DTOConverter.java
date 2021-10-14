package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.*;
import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.entities.Ticket;
import com.future.cinemaxx.entities.Projection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {
    ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public MovieDTO convertToMovieDTO(Movie movie){
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDurationInMinutes((int)movie.getDuration().toMinutes());
        movieDTO.setCategory(modelMapper.map(movie.getCategory(), CategoryDTO.class));
        movieDTO.setGenre(modelMapper.map(movie.getGenre(), GenreDTO.class));
        return movieDTO;
    }

    public Movie convertToMovie(MovieDTO movieDTO){
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        movie.setDuration(Duration.ofMinutes(movieDTO.getDurationInMinutes()));
        return movie;
    }

    public TheaterDTO convertToTheaterDTO(Theater theater){
        TheaterDTO theaterDTO = modelMapper.map(theater, TheaterDTO.class);
        theaterDTO.setCinemaHallDTOList(
                theater.getCinemaHalls().stream()
                        .map(cinemaHall -> convertToCinemaHallDTO(cinemaHall))
                        .collect(Collectors.toList()));

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

    public CinemaHallDTO convertToCinemaHallDTO(CinemaHall cinemaHall) {
        CinemaHallDTO cinemaHallDTO = modelMapper.map(cinemaHall, CinemaHallDTO.class);
        cinemaHallDTO.setProjectionDTOS(
                cinemaHall.getProjections().stream()
                .map(projection -> convertToProjectionDTO(projection)).collect(Collectors.toList())
        );
        return cinemaHallDTO;
    }

    public CinemaHall convertToCinemaHall(CinemaHallDTO cinemaHallDTO) {
        return modelMapper.map(cinemaHallDTO, CinemaHall.class);
    }

    public ProjectionDTO convertToProjectionDTO(Projection projection){
        ProjectionDTO projectionDTO = modelMapper.map(projection,ProjectionDTO.class);
        projectionDTO.setMovie(modelMapper.map(projection.getMovie(), MovieDTO.class));
        projectionDTO.setCinemaHallName(projection.getHall().getName());
        return projectionDTO;
    }

    public Projection convertToProjection(ProjectionDTO projectionDTO){return modelMapper.map(projectionDTO,Projection.class);}

    public List<ProjectionDTO> convertToListProjectionDTO(List<Projection> projections){
        return projections.stream()
                .map(this::convertToProjectionDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO convertToCategoryDTO(Category category){
        return modelMapper.map(category,CategoryDTO.class);
    }

    public Category covertToCategory(CategoryDTO categoryDTO){return modelMapper.map(categoryDTO, Category.class);}
}
