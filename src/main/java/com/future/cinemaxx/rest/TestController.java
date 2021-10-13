package com.future.cinemaxx.rest;

import com.future.cinemaxx.dtos.MovieDetails;
import com.future.cinemaxx.entities.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "api/test")
public class TestController {
    private static final String apiKey = "k_wkymfpql";

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = "/searchMovie")
    public ResponseEntity<Object> getMovieDetails(@RequestParam String title){
        String url = "https://imdb-api.com/en/API/Title/" + apiKey + "/" + "tt1375666";
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject(url, Object.class);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
