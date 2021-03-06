package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.*;
import com.future.cinemaxx.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    ModelMapper modelMapper;

    @Autowired
    public DTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MovieDTO convertToMovieDTO(Movie movie) {
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        movieDTO.setDurationInMinutes((int) movie.getDuration().toMinutes());
        movieDTO.setCategory(convertToCategoryDTO(movie.getCategory()));
        movieDTO.setGenre(convertToGenreDTO(movie.getGenre()));
        return movieDTO;
    }

    public Movie convertToMovie(MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        movie.setDuration(Duration.ofMinutes(movieDTO.getDurationInMinutes()));
        return movie;
    }

    public TheaterDTO convertToTheaterDTO(Theater theater) {
        TheaterDTO theaterDTO = modelMapper.map(theater, TheaterDTO.class);
        theaterDTO.setCinemaHallDTOList(
                theater.getCinemaHalls().stream()
                        .map(cinemaHall -> convertToCinemaHallDTO(cinemaHall))
                        .collect(Collectors.toList()));

        return theaterDTO;
    }

    public Theater convertToTheater(TheaterDTO theaterDTO) {
        return modelMapper.map(theaterDTO, Theater.class);
    }

    public TicketDTO convertToTicketDTO(Ticket ticket) {
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        ticketDTO.setProjection(convertToProjectionDTO(ticket.getProjection()));
        return ticketDTO;
    }

    public Ticket convertToTicket(TicketDTO ticketDTO) {
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

    public ProjectionDTO convertToProjectionDTO(Projection projection) {
        ProjectionDTO projectionDTO = modelMapper.map(projection, ProjectionDTO.class);
        projectionDTO.setMovie(convertToMovieDTO(projection.getMovie()));
        projectionDTO.setCinemaHallName(projection.getHall().getName());
        return projectionDTO;
    }

    public Projection convertToProjection(ProjectionDTO projectionDTO) {
        return modelMapper.map(projectionDTO, Projection.class);
    }

    public List<ProjectionDTO> convertToListProjectionDTO(List<Projection> projections) {
        return projections.stream()
                .map(this::convertToProjectionDTO)
                .collect(Collectors.toList());
    }

    public List<TicketDTO> convertToListTicketDTO(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::convertToTicketDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO convertToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category covertToCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    public GenreDTO convertToGenreDTO(Genre genre) {
        return modelMapper.map(genre, GenreDTO.class);
    }

    public Genre convertToGenre(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }
}
