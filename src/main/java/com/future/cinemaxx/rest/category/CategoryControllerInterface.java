package com.future.cinemaxx.rest.category;

import com.future.cinemaxx.dtos.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryControllerInterface {

    @GetMapping
    List<CategoryDTO> getAll();

    @GetMapping("/{id}")
    CategoryDTO getById(@PathVariable int id);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDTO create(@RequestBody CategoryDTO categoryDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") int id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    CategoryDTO update(@PathVariable("id") int id, @RequestBody CategoryDTO categoryDTO);

}
