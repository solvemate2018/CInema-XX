package com.future.cinemaxx.rest.theater;

import com.future.cinemaxx.dtos.GenreDTO;
import com.future.cinemaxx.dtos.TheaterDTO;
import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import com.future.cinemaxx.security.entities.ERole;
import com.future.cinemaxx.security.entities.Role;
import com.future.cinemaxx.security.entities.User;
import com.future.cinemaxx.security.payload.request.LoginRequest;
import com.future.cinemaxx.security.payload.response.JwtResponse;
import com.future.cinemaxx.security.repositories.RoleRepository;
import com.future.cinemaxx.security.repositories.UserRepository;
import com.future.cinemaxx.testUtils.TestDataMaker;
import org.aspectj.lang.annotation.After;
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
class TheaterControllerImplTest {
    private final String BASE_PATH = "/api/theater";
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

    TheaterRepo theaterRepo;

    //Used for storing ids
    ArrayList<Integer> ids;

    @Autowired
    public TheaterControllerImplTest(TestRestTemplate restTemplate, TheaterRepo theaterRepo) {
        this.restTemplate = restTemplate;
        this.theaterRepo = theaterRepo;
    }

    @BeforeEach
    void setUpTheater(){
        ids = TestDataMaker.setUpTheaters(theaterRepo);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(adminRole);
        User admin = new User("admin", "admin@a.dk", encoder.encode("test"));
        admin.addRole(adminRole);
        userRepository.save(admin);
        securityToken = "Bearer "+ login("admin","test").getBody().getAccessToken();
        headersForRequest = new HttpHeaders();
        headersForRequest.add("Authorization",securityToken);
    }
    @AfterEach
    public void clear(){
        TestDataMaker.clearTheaters(theaterRepo,userRepository,roleRepository);
    }

    @Test
    void getAll() {
        List<Theater> theaters=theaterRepo.findAll();
        ResponseEntity<List<TheaterDTO>> response = getResponseFromAllTheaters();
        assertEquals(2,response.getBody().size());
        assertEquals(theaters.size(), response.getBody().size());
    }
    @Test
    void getTheaterById() {
        int id = ids.get(0);
        Theater theater = theaterRepo.findById(id).orElse(null);
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<TheaterDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/"+id),
                HttpMethod.GET,
                entity,
                TheaterDTO.class);
        assertEquals(theater.getName(),response.getBody().getName());
        assertEquals(theater.getStreet(),response.getBody().getStreet());
        assertEquals("Cinema city",response.getBody().getName());
    }


    @Test
    void delete() {
        List<Theater> theaters= theaterRepo.findAll();
        int sizeBefore = theaters.size();
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<TheaterDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/"+ids.get(0)),
                HttpMethod.DELETE,
                entity,
                TheaterDTO.class);
        ResponseEntity<List<TheaterDTO>> res = getResponseFromAllTheaters();
        assertEquals(1,res.getBody().size());
        assertEquals(1, sizeBefore-res.getBody().size());
    }

    private String makeUrl(String path) {
        String pathBuilt = "http://localhost:" + port + path;
        return pathBuilt;
    }

    private ResponseEntity<List<TheaterDTO>> getResponseFromAllTheaters() {
        HttpEntity<String> entity = new HttpEntity<>(null, headersForRequest);
        ResponseEntity<List<TheaterDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<TheaterDTO>>() {
                });
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