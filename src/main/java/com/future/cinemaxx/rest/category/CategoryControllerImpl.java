package com.future.cinemaxx.rest.category;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.categories.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryControllerImpl implements CategoryControllerInterface {

    @Autowired
    CategoryServiceImpl categoryService;
    DTOConverter dtoConverter;

    @Autowired
    public CategoryControllerImpl(CategoryServiceImpl categoryService, DTOConverter dtoConverter){
        this.categoryService = categoryService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategories().stream()
                .map(category -> dtoConverter.convertToCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getById(int id) {
        return dtoConverter.convertToCategoryDTO(categoryService.getById(id));
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        return dtoConverter.convertToCategoryDTO(categoryService.create(dtoConverter.covertToCategory(categoryDTO)));
    }

    @Override
    public void deleteById(int id) {
        categoryService.deleteCategory(id);
    }

    @Override
    public CategoryDTO update(int id, CategoryDTO categoryDTO) {
        return dtoConverter.convertToCategoryDTO(categoryService.updateCategory(id, dtoConverter.covertToCategory(categoryDTO)));
    }
}
