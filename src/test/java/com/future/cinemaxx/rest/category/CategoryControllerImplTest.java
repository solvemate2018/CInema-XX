package com.future.cinemaxx.rest.category;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.entities.Category;
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
class CategoryControllerImplTest {

    private final String BASE_PATH = "/api/category";
    private final HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    //Used to set up mock data for testing

    CategoryRepo categoryRepo;

    ArrayList<Integer> ids;

    @Autowired
    public CategoryControllerImplTest(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @BeforeEach
    void setUpCategories(){
        ids = TestDataMaker.setUpCategories(categoryRepo);
    }
    @AfterEach
    void clear(){
        TestDataMaker.clearCategory(categoryRepo);
    }

    @Test
    void getAll() {
        ResponseEntity<List<CategoryDTO>> response = getResponseFromAllCategories();
        List<Category> categories = categoryRepo.findAll();
        assertEquals(4,response.getBody().size());
        assertEquals(response.getBody().get(0).getName(), "A");
        assertEquals(categories.size(),response.getBody().size());
    }

    @Test
    void getById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<CategoryDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids.get(0)),
                HttpMethod.GET,
                entity,
                CategoryDTO.class);
        assertEquals("A",response.getBody().getName());
        assertEquals(8,response.getBody().getAgeLimit());
    }

    @Test
    void create() {
        CategoryDTO newCategory = new CategoryDTO();
        newCategory.setAgeLimit(23);
        newCategory.setName("R+");
        HttpEntity<CategoryDTO> entity = new HttpEntity<CategoryDTO>(newCategory,headers);
        ResponseEntity<CategoryDTO> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.POST,
                entity,
                CategoryDTO.class);
        ResponseEntity<List<CategoryDTO>> res = getResponseFromAllCategories();
        assertEquals(res.getBody().size(), 5);
        assertEquals("R+", response.getBody().getName());
        assertEquals(23, response.getBody().getAgeLimit());

    }

    @Test
    void deleteById() {
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        ResponseEntity<CategoryDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+ "/"+ids.get(0)),
                HttpMethod.DELETE,
                entity,
                CategoryDTO.class);
        ResponseEntity<List<CategoryDTO>> res = getResponseFromAllCategories();
        assertEquals(3,res.getBody().size());
    }

    @Test
    void update() {
        int id = ids.get(0);
        CategoryDTO categoryToEdit = new CategoryDTO();
        categoryToEdit.setName("ASD");
        categoryToEdit.setAgeLimit(200);
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id",id);


        HttpEntity<CategoryDTO> entity = new HttpEntity<CategoryDTO>(categoryToEdit,headers);
        ResponseEntity<CategoryDTO> res = restTemplate.exchange(makeUrl(BASE_PATH+"/{id}"),
                HttpMethod.PUT ,
                entity,
                CategoryDTO.class,
                param);

        ResponseEntity<CategoryDTO> response = restTemplate.exchange(makeUrl(BASE_PATH+"/"+id),
                HttpMethod.GET,
                entity,
                CategoryDTO.class);
        assertEquals(200,response.getBody().getAgeLimit());
        assertEquals("ASD", response.getBody().getName());
    }

    private String makeUrl(String path) {
        String pathBuilt = "http://localhost:" + port + path;
        return pathBuilt;
    }

    private ResponseEntity<List<CategoryDTO>> getResponseFromAllCategories() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(makeUrl(BASE_PATH),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CategoryDTO>>() {
                });
        return response;
    }

}