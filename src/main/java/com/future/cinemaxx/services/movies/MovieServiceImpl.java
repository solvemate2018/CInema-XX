package com.future.cinemaxx.services.movies;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieServiceInterface {
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    GenreRepo genreRepo;
    CategoryRepo categoryRepo;

    public MovieServiceImpl(MovieRepo movieRepo, GenreRepo genreRepo, CategoryRepo categoryRepo, ProjectionRepo projectionRepo){
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.categoryRepo = categoryRepo;
        this.projectionRepo = projectionRepo;
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
        if(projectionRepo.existsByMovie(
                movieRepo.findById(movieId).orElseThrow(()-> new ResourceNotFoundException())))
            throw new IllegalStateException("There are projections using this movie");
        movieRepo.deleteById(movieId);
    }

    @Override
    public Movie updateMovie(int id, Movie movie) {
        Movie updatedMovie = movieRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with this id does not exist"));

        if (movie.getName()!=null){ updatedMovie.setName(movie.getName()); }
        if (movie.getDuration()!=null){ updatedMovie.setDuration(movie.getDuration()); }

        if (movie.getGenre()!=null){
            Genre genre = genreRepo.findGenreByName(movie.getGenre().getName());
            if(genre==null){
                throw new ResourceNotFoundException("Genre with this name does not exist");
            }else{
                updatedMovie.setGenre(genre);
            }
        }
        if (movie.getCategory()!=null){
            Category category = categoryRepo.findCategoryByName(movie.getCategory().getName());
            if(category==null){
                throw new ResourceNotFoundException("The category with this name does not exist");
            }else{
                updatedMovie.setCategory(category);
            }
        }
        return movieRepo.save(updatedMovie);
    }

}
