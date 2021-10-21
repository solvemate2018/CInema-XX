package com.future.cinemaxx.services.movies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import com.future.cinemaxx.services.imdb.ImdbMovieServiceInteface;
import com.sun.xml.bind.v2.runtime.IllegalAnnotationsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieServiceInterface {
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    GenreRepo genreRepo;
    CategoryRepo categoryRepo;
    ImdbMovieServiceInteface imdbMovieService;
    @Value("${app.ImdbApiKey}")
    private String apiKey;

    public MovieServiceImpl(MovieRepo movieRepo, GenreRepo genreRepo,
                            CategoryRepo categoryRepo, ProjectionRepo projectionRepo, ImdbMovieServiceInteface imdbMovieService) {
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.categoryRepo = categoryRepo;
        this.projectionRepo = projectionRepo;
        this.imdbMovieService = imdbMovieService;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Movie getMovieById(int id) {
        return movieRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is not such Movie in our system"));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie createMovie(Movie movie, int genreId, int categoryId) {
        if(movie.getDuration().isNegative()){
            throw new IllegalArgumentException("The duration of a movie cannot be a negative number");
        }
        return movieRepo.save(new Movie(movie.getName(),
                movie.getDuration(),
                genreRepo.findById(genreId).orElseThrow(() -> new ResourceNotFoundException("There is not such Genre in our system")),
                categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("There is not such Category in our system"))));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMovie(int movieId) {
        if (projectionRepo.existsByMovie(
                movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("There is not such movie in our system!"))))
            throw new IllegalStateException("There are projections using this movie");
        movieRepo.deleteById(movieId);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie updateMovie(int id, Movie movie) {
        Movie updatedMovie = movieRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with this id does not exist"));

        if (movie.getName() != null) {
            updatedMovie.setName(movie.getName());
        }
        else{
            throw new IllegalArgumentException("The name should not be null. Try including it in the request!");
        }
        if (movie.getDuration() != null || movie.getDuration().isNegative()) {
            updatedMovie.setDuration(movie.getDuration());
        }
        else{
            throw new IllegalArgumentException("The duration cannot be negative.");
        }

        if (movie.getGenre() != null) {
            Genre genre = genreRepo.findGenreByName(movie.getGenre().getName());
            if (genre == null) {
                throw new ResourceNotFoundException("Genre with this name does not exist");
            } else {
                updatedMovie.setGenre(genre);
            }
        }
        if (movie.getCategory() != null) {
            Category category = categoryRepo.findCategoryByName(movie.getCategory().getName());
            if (category == null) {
                throw new ResourceNotFoundException("The category with this name does not exist");
            } else {
                updatedMovie.setCategory(category);
            }
        }
        return movieRepo.save(updatedMovie);
    }

    @Override
    public MovieDetails getMovieDetails(int movieId) throws JsonProcessingException {
        return imdbMovieService.getMovieDetails(movieId);
    }
}
