package com.future.cinemaxx.rest.genre;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.GenreDTO;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.GenreRepo;
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
class GenreControllerImplTest {

    private final String BASE_PATH = "/api/genre";
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

    GenreRepo genreRepo;

    ArrayList<Integer> ids;

    @Autowired
    public GenreControllerImplTest(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @BeforeEach
    void setUpCategories(){
        ids = TestDataMaker.setUpGenres(genreRepo);
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
    void clear(){
        TestDataMaker.clearGenre(genreRepo,userRepository,roleRepository);
    }

    @Test
    void getAll() {
        ResponseEntity<List<GenreDTO>> response = getResponseFromAllGenres();
        assertEquals(4,response.getBody().size());
        assertEquals("Horror",response.getBody().get(0).getName());
    }

    @Test
    void getById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<GenreDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids.get(0)),
                HttpMethod.GET,
                entity,
                GenreDTO.class);
        assertEquals("Horror",response.getBody().getName());
    }

    @Test
    void create() {
        GenreDTO newGenre = new GenreDTO();
        newGenre.setName("Bad Rom-com");
        HttpEntity<GenreDTO> entity = new HttpEntity<GenreDTO>(newGenre,headersForRequest);
        ResponseEntity<GenreDTO> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.POST,
                entity,
                GenreDTO.class);
        ResponseEntity<List<GenreDTO>> res = getResponseFromAllGenres();
        assertEquals(res.getBody().size(), 5);
        assertEquals("Bad Rom-com", response.getBody().getName());
    }

    @Test
    void delete() {
        HttpEntity<String> entity = new HttpEntity<>(null,headersForRequest);
        ResponseEntity<GenreDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids.get(0)),
                HttpMethod.DELETE,
                entity,
                GenreDTO.class);
        ResponseEntity<List<GenreDTO>> res = getResponseFromAllGenres();
        assertEquals(3,res.getBody().size());
    }

    @Test
    void update() {
        int id = ids.get(0);
        GenreDTO genreToEdit = new GenreDTO();
        genreToEdit.setName("Bad Movie");
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id",id);

        HttpEntity<GenreDTO> entity = new HttpEntity<GenreDTO>(genreToEdit,headersForRequest);
        ResponseEntity<GenreDTO> res = restTemplate.exchange(makeUrl(BASE_PATH+"/{id}"),
                HttpMethod.PUT ,
                entity,
                GenreDTO.class,
                param);

        ResponseEntity<GenreDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/"+id),
                HttpMethod.GET,
                entity,
                GenreDTO.class);
        assertEquals("Bad Movie", response.getBody().getName());
    }

    private String makeUrl(String path) {
        String pathBuilt = "http://localhost:" + port + path;
        return pathBuilt;
    }

    private ResponseEntity<List<GenreDTO>> getResponseFromAllGenres() {
        HttpEntity<String> entity = new HttpEntity<>(null, headersForRequest);
        ResponseEntity<List<GenreDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<GenreDTO>>() {
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