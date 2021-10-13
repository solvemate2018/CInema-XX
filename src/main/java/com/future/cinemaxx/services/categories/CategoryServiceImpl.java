package com.future.cinemaxx.services.categories;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.repositories.CategoryRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public class CategoryServiceImpl implements CategoryServiceInterface {
    private CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {return categoryRepo.findAll();}

    @Override
    public Category addCategory(String name, int ageLimit) {
        return categoryRepo.save(new Category(name,ageLimit));
    }

    @Override
    public void deleteCategory(int id) {
        if(!categoryRepo.existsById(id)){throw new ResourceNotFoundException();}
        categoryRepo.deleteById(id);
    }
}
