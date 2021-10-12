package com.future.cinemaxx.dtos.converter;

import com.future.cinemaxx.dtos.*;
import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
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

    public ProjectionDTO convertToProjectionDTO(Projection projection){
        ProjectionDTO projectionDTO = modelMapper.map(projection,ProjectionDTO.class);
        projectionDTO.setMovie(modelMapper.map(projection.getMovie(), MovieDTO.class));
        projectionDTO.setHall(modelMapper.map(projection.getHall(), CinemaHallDTO.class));
        return projectionDTO;
    }
    public Projection convertToProjection(ProjectionDTO projectionDTO){return modelMapper.map(projectionDTO,Projection.class);}

    public CategoryDTO convertToCategoryDTO(Category category){
        CategoryDTO categoryDTO = modelMapper.map(category,CategoryDTO.class);
        return categoryDTO;
    }
    public Category covertToCategory(CategoryDTO categoryDTO){return modelMapper.map(categoryDTO, Category.class);}
}
