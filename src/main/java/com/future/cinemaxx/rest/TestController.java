package com.future.cinemaxx.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.services.movies.MovieServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ObjectStreamField;

@RestController
@RequestMapping(path = "api/test")
public class TestController {
    private static final String apiKey = "k_wkymfpql";

    MovieServiceImpl movieService;
    ObjectMapper objectMapper;

    public TestController(MovieServiceImpl movieService, ObjectMapper objectMapper){
        this.movieService = movieService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/searchMovie/{movieId}")
    public ResponseEntity<MovieDetails> getMovieDetails(@PathVariable int movieId) throws JsonProcessingException {
        String title = movieService.getMovieById(movieId).getName();
        String searchForMovieUrl = "https://imdb-api.com/en/API/Search/" + apiKey + "/" + title;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchForMovieUrl, String.class);
        JsonNode searchRoot = objectMapper.readTree(searchResponse.getBody());
        
        String imDbId = getMovieIdFromJson(searchRoot);
        
        String getDetailsUrl = "https://imdb-api.com/en/API/Title/" + apiKey + "/" + imDbId;
        ResponseEntity<String> detailsResponse = restTemplate.getForEntity(getDetailsUrl, String.class);
        JsonNode detailsRoot = objectMapper.readTree(detailsResponse.getBody());
        MovieDetails details = new MovieDetails(detailsRoot);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    private String getMovieIdFromJson(JsonNode searchRoot) {
        JsonNode searchResults = searchRoot.get("results");
        return searchResults.get(0).get("id").asText();
    }
}
