package com.future.cinemaxx.rest.projection;

import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.security.entities.ERole;
import com.future.cinemaxx.security.entities.Role;
import com.future.cinemaxx.security.entities.User;
import com.future.cinemaxx.security.payload.request.LoginRequest;
import com.future.cinemaxx.security.payload.response.JwtResponse;
import com.future.cinemaxx.security.repositories.RoleRepository;
import com.future.cinemaxx.security.repositories.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@EnableAutoConfiguration
@SpringBootTest(classes = {com.future.cinemaxx.CinemaxxApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.properties"})
@WithMockUser
class ProjectionControllerImplTest {

    private final String BASE_PATH = "/api/projection";
    private final HttpHeaders headers = new HttpHeaders();
    private String securityToken;
    private HttpHeaders headersForRequest;
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    //For creating a user
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;


    //Used to set up mock data for testing

    CategoryRepo categoryRepo;
    CinemaHallRepo cinemaHallRepo;
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    TheaterRepo theaterRepo;
    GenreRepo genreRepo;
    TicketRepo ticketRepo;

    //Used for storing the ids of theaters and projections no matter what are they generated as

    ArrayList<Integer>[] ids;

    @Autowired
    public ProjectionControllerImplTest(CategoryRepo categoryRepo, CinemaHallRepo cinemaHallRepo, MovieRepo movieRepo,
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
    public void clear(){
        TestDataMaker.clear(theaterRepo,cinemaHallRepo,categoryRepo,genreRepo,movieRepo,projectionRepo,ticketRepo,userRepository,roleRepository);
    }

    @BeforeEach
    void createData(){
        ids= TestDataMaker.makeDataForTests(theaterRepo,cinemaHallRepo,categoryRepo,genreRepo,movieRepo,projectionRepo,ticketRepo);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(adminRole);
        User admin = new User("admin", "admin@a.dk", encoder.encode("test"));
        admin.addRole(adminRole);
        userRepository.save(admin);
        securityToken = "Bearer "+ login("admin","test").getBody().getAccessToken();
        headersForRequest = new HttpHeaders();
        headersForRequest.add("Authorization",securityToken);
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
        ResponseEntity<ProjectionDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids[3].get(3)),
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
    @Test
    void getByHallId() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH+"/hall/"+ids[1].get(0)),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>(){});
        assertEquals("Scary movie",response.getBody().get(0).getMovie().getName());
        assertEquals("Spider-man",response.getBody().get(1).getMovie().getName());
        assertEquals("A",response.getBody().get(0).getCinemaHallName());
        assertEquals(4,response.getBody().size());
    }

    @Test
    void getByTheaterIdAndHallName() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH+"/theater/"+ids[0].get(0)+"/hall/A"),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>(){});
        assertEquals("Scary movie",response.getBody().get(0).getMovie().getName());
        assertEquals("Spider-man",response.getBody().get(1).getMovie().getName());
        assertEquals("A",response.getBody().get(0).getCinemaHallName());
        assertEquals(4,response.getBody().size());
    }

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
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        LocalDate dateFrom = LocalDate.parse("2025-11-12");
        LocalDate dateTo = LocalDate.parse("2025-11-14");
        int id = ids[0].get(1);
        String url = makeUrl(BASE_PATH+"/theater/{id}/getByTwoDates");
        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("id", id);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo",dateTo);

        System.out.println(builder.buildAndExpand(urlParams).toUri());

        ResponseEntity<List<ProjectionDTO>> response = restTemplate.exchange(builder
                        .buildAndExpand(urlParams).toUri(),HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProjectionDTO>>(){});
        assertEquals(3, response.getBody().size());
        assertEquals("Deadpool",response.getBody().get(2).getMovie().getName());
    }

    @Test
    void deleteProjectionById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<ProjectionDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids[3].get(0)),
                HttpMethod.DELETE,
                entity,
                ProjectionDTO.class);
        ResponseEntity<List<ProjectionDTO>> res = getResponseFromAllProjections();
        assertEquals(19,res.getBody().size());
    }

    @Test
    void updateProjection() {
        LocalDateTime time = LocalDateTime.parse("2021-11-27T12:30:00");
        int id = ids[3].get(0);
        ProjectionDTO projectionToEdit = new ProjectionDTO();
        projectionToEdit.setTicketPrice(200);

        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id",id);

        HttpEntity<ProjectionDTO> entity = new HttpEntity<ProjectionDTO>(projectionToEdit,headersForRequest);
        ResponseEntity<ProjectionDTO> res = restTemplate.exchange(makeUrl(BASE_PATH+"/{id}/movie/"+ids[2].get(0)+"/hall/"+ids[1].get(0)),
                HttpMethod.PUT ,
                entity,
                ProjectionDTO.class,
                param);

        ResponseEntity<ProjectionDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/"+id),
                HttpMethod.GET,
                entity,
                ProjectionDTO.class);
        assertEquals(200,response.getBody().getTicketPrice());
        assertEquals(response.getBody().getStartTime(),time);
    }

    @Test
    void createProjection() {
        LocalDateTime time = LocalDateTime.parse("2029-11-11T12:30:00");
        ProjectionDTO newProjection = new ProjectionDTO();
        newProjection.setStartTime(time);
        newProjection.setTicketPrice(16f);
        HttpEntity<ProjectionDTO> entity = new HttpEntity<ProjectionDTO>(newProjection,headersForRequest);
        ResponseEntity<ProjectionDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/movie/"+ids[2].get(6)+"/hall/"+ids[1].get(0)),
                HttpMethod.POST,
                entity,
                ProjectionDTO.class);
        ResponseEntity<List<ProjectionDTO>> res = getResponseFromAllProjections();
        assertEquals(res.getBody().size(), 21);
        assertEquals(time,response.getBody().getStartTime());
        assertEquals("A", response.getBody().getCinemaHallName());
        assertEquals("Deadpool", response.getBody().getMovie().getName());

    }

    private String makeUrl(String path){
        String pathBuilt = "http://localhost:"+port+path;
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
    //Utility method to login and store the return the received response, which includes the securityToken
    private   ResponseEntity<JwtResponse>  login(String userName, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userName);
        loginRequest.setPassword(password);

        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest,headers);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(makeUrl("/api/auth/signin"),
                HttpMethod.POST,
                entity,
                JwtResponse.class);
        return response;
    }
}