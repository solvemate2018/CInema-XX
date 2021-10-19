package com.future.cinemaxx.rest.ticket;


import com.future.cinemaxx.dtos.TicketDTO;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.testUtils.TestDataMaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureTestDatabase
@EnableAutoConfiguration
@SpringBootTest(classes = {com.future.cinemaxx.CinemaxxApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.properties"})
@WithMockUser
class TicketControllerImplTest {
    private final String BASE_PATH = "/api/ticket";
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;
    //Used to set up mock data for testing

    CategoryRepo categoryRepo;
    CinemaHallRepo cinemaHallRepo;
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    TheaterRepo theaterRepo;
    GenreRepo genreRepo;
    TicketRepo ticketRepo;


    //Used for keeping the ids needed for tests
    ArrayList<Integer> ids;

    @Autowired
    public TicketControllerImplTest(CategoryRepo categoryRepo, CinemaHallRepo cinemaHallRepo, MovieRepo movieRepo,
                                        ProjectionRepo projectionRepo, TheaterRepo theaterRepo, GenreRepo genreRepo,

                                        TicketRepo ticketRepo,TestRestTemplate restTemplate) {
        this.categoryRepo = categoryRepo;
        this.cinemaHallRepo = cinemaHallRepo;
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.theaterRepo = theaterRepo;
        this.genreRepo = genreRepo;
        this.restTemplate = restTemplate;
        this.ticketRepo = ticketRepo;
    }
    @AfterEach
    void clear(){
        TestDataMaker.clear(theaterRepo,cinemaHallRepo,categoryRepo,genreRepo,movieRepo,projectionRepo,ticketRepo);
    }

    @BeforeEach
    void createData(){
        ids= TestDataMaker.setUpTickets(theaterRepo,cinemaHallRepo,categoryRepo,genreRepo,movieRepo,projectionRepo,ticketRepo);
    }

    @Test
    void bookTicket() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<TicketDTO> res = restTemplate.exchange(makeUrl(BASE_PATH+"/book/"+ids.get(2)
                        +"/row/6/column/7"),
                HttpMethod.PUT ,
                entity,
                TicketDTO.class);

        ResponseEntity<TicketDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/projection/"+ids.get(2)
                        +"/row/6/column/7"),
                HttpMethod.GET,
                entity,
                TicketDTO.class);

        assertTrue(response.getBody().isSold());
    }

    @Test
    void getAll() {
        ResponseEntity<List<TicketDTO>> response = getResponseFromAllTickets();
        assertEquals(480,response.getBody().size());
    }

    @Test
    void getTicketByProjectionRowAndColumn() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<TicketDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/projection/"+ids.get(2)
                        +"/row/6/column/7"),
                HttpMethod.GET,
                entity,
                TicketDTO.class);
        assertEquals("Nightmare On Elm Street",response.getBody().getProjection().getMovie().getName());
        assertEquals(6,response.getBody().getTicketRow());
        assertEquals(7,response.getBody().getTicketColumn());
    }

    @Test
    void getTicketsByProjectionId() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<List<TicketDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH+"/projection/"+ids.get(1)),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<TicketDTO>>(){});
        assertEquals("Up",response.getBody().get(0).getProjection().getMovie().getName());
        assertEquals(96,response.getBody().size());

    }
    private String makeUrl(String path) {
        String pathBuilt = "http://localhost:" + port + path;
        return pathBuilt;
    }
    private ResponseEntity<List<TicketDTO>> getResponseFromAllTickets() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<TicketDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<TicketDTO>>() {
                });
        return response;
    }
}