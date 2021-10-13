package com.future.cinemaxx.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.cinemaxx.dtos.ActorsInfo;
import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.entities.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ObjectStreamField;

@RestController
@RequestMapping(path = "api/test")
public class TestController {
    private static final String apiKey = "k_wkymfpql";

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(path = "/searchMovie")
    public ResponseEntity<MovieDetails> getMovieDetails(String title) throws JsonProcessingException {


        String url = "https://imdb-api.com/en/API/Title/" + apiKey + "/" + "tt1375666";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JsonNode root = objectMapper.readTree(response.getBody());
        MovieDetails details = new MovieDetails(root);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
