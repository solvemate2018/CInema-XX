package com.future.cinemaxx.services.movies;

import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieServiceInterface {
    MovieRepo movieRepo;
    GenreRepo genreRepo;
    CategoryRepo categoryRepo;

    public MovieServiceImpl(MovieRepo movieRepo, GenreRepo genreRepo, CategoryRepo categoryRepo){
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Movie getMovieById(int id) {
        return movieRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Movie createMovie(Movie movie, int genreId, int categoryId) {
        return movieRepo.save(new Movie(movie.getName(),
                movie.getDuration(),
                genreRepo.findById(genreId).orElseThrow(ResourceNotFoundException::new),
                categoryRepo.findById(categoryId).orElseThrow(ResourceNotFoundException::new)));
    }

    @Override
    public void deleteMovie(int movieId) {
        if(!movieRepo.existsById(movieId)) throw new ResourceNotFoundException();
        movieRepo.deleteById(movieId);
    }
}
