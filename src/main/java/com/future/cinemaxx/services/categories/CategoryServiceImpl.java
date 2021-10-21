package com.future.cinemaxx.services.categories;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.repositories.CategoryRepo;
import com.future.cinemaxx.repositories.MovieRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryServiceImpl implements CategoryServiceInterface {
    private final CategoryRepo categoryRepo;
    private final MovieRepo movieRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo, MovieRepo movieRepo) {
        this.categoryRepo = categoryRepo;
        this.movieRepo = movieRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getById(int id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no category with id:" + id));
    }

    @Override
    public Category create(Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new IllegalArgumentException("A category with name: " + category.getName() + " already exists");
        }
        else if(category.getAgeLimit() < 0){
            throw new IllegalArgumentException("Invalid age limit: " + category.getAgeLimit());
        }
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(int categoryId, Category category) {
        Category updatedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException());
        if (category.getName() != null) {
            updatedCategory.setName(category.getName());
        }
        else{
            throw new IllegalArgumentException("The name of the category is required!");
        }
        if (category.getAgeLimit() > 0) {
            updatedCategory.setAgeLimit(category.getAgeLimit());
        }
        else{
            throw new IllegalArgumentException("The age limit must be bigger then 0");
        }
        return categoryRepo.save(updatedCategory);
    }

    @Override
    public void deleteCategory(int id) {
        if (!movieRepo.existsByCategory(categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is not such Category"))))
        categoryRepo.deleteById(id);
        else{
            throw new IllegalStateException("There are movies using this Category. You need to delete them first!");
        }
    }
}
