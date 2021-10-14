package com.future.cinemaxx.services.movies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import com.future.cinemaxx.repositories.ProjectionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieServiceInterface {
    @Value("app.ImdbApiKey")
    private String apiKey;

    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    GenreRepo genreRepo;
    CategoryRepo categoryRepo;
    ObjectMapper objectMapper;

    public MovieServiceImpl(MovieRepo movieRepo, GenreRepo genreRepo,
                            CategoryRepo categoryRepo, ProjectionRepo projectionRepo
                            , ObjectMapper objectMapper){
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.categoryRepo = categoryRepo;
        this.projectionRepo = projectionRepo;
        this.objectMapper = objectMapper;
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

    @Override
    public MovieDetails getMovieDetails(int movieId) throws JsonProcessingException {
        String title = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException()).getName();
        String searchForMovieUrl = "https://imdb-api.com/en/API/Search/" + apiKey + "/" + title;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchForMovieUrl, String.class);
        JsonNode searchRoot = objectMapper.readTree(searchResponse.getBody());

        String imDbId = getMovieIdFromJson(searchRoot);

        String getDetailsUrl = "https://imdb-api.com/en/API/Title/" + apiKey + "/" + imDbId;
        ResponseEntity<String> detailsResponse = restTemplate.getForEntity(getDetailsUrl, String.class);
        JsonNode detailsRoot = objectMapper.readTree(detailsResponse.getBody());
        MovieDetails details = new MovieDetails(detailsRoot);
        return details;
    }

    private String getMovieIdFromJson(JsonNode searchRoot) {
        JsonNode searchResults = searchRoot.get("results");
        return searchResults.get(0).get("id").asText();
    }
}
