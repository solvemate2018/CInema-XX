package com.future.cinemaxx.services.movies;

import com.future.cinemaxx.entities.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MovieServiceInterface {
    List<Movie> getAllMovies();
    Movie getMovieById(int id);
    Movie createMovie(Movie movie, int genreId, int categoryId);
    void deleteMovie(int movieId);
    Movie updateMovie(int id, Movie movie);
}
