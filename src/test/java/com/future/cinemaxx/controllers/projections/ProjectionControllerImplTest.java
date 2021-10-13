package com.future.cinemaxx.controllers.projections;

import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.testUtils.TestDataMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@EnableAutoConfiguration
@SpringBootTest(classes = {com.future.cinemaxx.CinemaxxApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectionControllerImplTest {

    private final String BASE_PATH = "/api/projections";
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate;

    //Used to set up mock data for testing

    CategoryRepo categoryRepo;
    CinemaHallRepo cinemaHallRepo;
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    TheaterRepo theaterRepo;
    GenreRepo genreRepo;

    //Used for storing the ids of theaters and projections no matter what are they generated as

    ArrayList<Integer>[] ids;

    @Autowired
    public ProjectionControllerImplTest(CategoryRepo categoryRepo, CinemaHallRepo cinemaHallRepo, MovieRepo movieRepo,
                                        ProjectionRepo projectionRepo, TheaterRepo theaterRepo, GenreRepo genreRepo,
                                        TestRestTemplate restTemplate) {
        this.categoryRepo = categoryRepo;
        this.cinemaHallRepo = cinemaHallRepo;
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.theaterRepo = theaterRepo;
        this.genreRepo = genreRepo;
        this.restTemplate = restTemplate;
    }


    @BeforeEach
    public void setUpData(){
        ids= TestDataMaker.makeDataForTests(theaterRepo,cinemaHallRepo,categoryRepo,genreRepo,movieRepo,projectionRepo);
    }


    @Test
    void getAll() {
        ResponseEntity<List<ProjectionDTO>> response = getResponseFromAllProjections();
        assertEquals(20,response.getBody().size());
        assertEquals(response.getBody().get(0).getMovie().getName(), "Scary movie");
    }

    @Test
    void getById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<ProjectionDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids[1].get(3)),
                HttpMethod.GET,
                entity,
                ProjectionDTO.class);
        assertEquals("B",response.getBody().getCinemaHallName());
        assertEquals("Gladiator",response.getBody().getMovie().getName());
    }

    @Test
    void getByTheaterId() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH+"/theater/"+ids[0].get(0)),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>(){});
        assertEquals("Scary movie",response.getBody().get(0).getMovie().getName());
        assertEquals("Spider-man",response.getBody().get(1).getMovie().getName());
        assertEquals("Last movie",response.getBody().get(5).getMovie().getName());
        assertEquals("B",response.getBody().get(5).getCinemaHallName());
        assertEquals(9,response.getBody().size());

    }

    //UNFINISHED.. WIERD ERROR

    @Test
    void getByDateAndTheaterId() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        LocalDate date = LocalDate.parse("2025-11-12");
        int id = ids[0].get(1);
        String url = makeUrl(BASE_PATH+"/theater/{id}/getByDate");
        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("id", id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("date", date);

        System.out.println(builder.buildAndExpand(urlParams).toUri());

        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(builder
                .buildAndExpand(urlParams).toUri(),HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>(){});

        assertEquals("Dark Knight", response.getBody().get(0).getMovie().getName());
    }


    @Test
    void getProjectionsBetweenDates() {
    }

    private String makeUrl(String path){
        String pathBuilt = "http://localhost:"+port+path;
        System.out.println(pathBuilt);
        return pathBuilt;
    }

    private ResponseEntity<List<ProjectionDTO>> getResponseFromAllProjections() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>() {});
        return response;
    }

}