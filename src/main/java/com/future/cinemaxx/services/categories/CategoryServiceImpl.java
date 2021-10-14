package com.future.cinemaxx.services.categories;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.repositories.CategoryRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryServiceInterface {
    private CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {return categoryRepo.findAll();}

    @Override
    public Category getById(int id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no category with id:" + id));
    }

    @Override
    public Category create(Category category) {
        if(categoryRepo.existsByName(category.getName())){
            throw new IllegalArgumentException("A category with name: "+ category.getName()+" already exists");}
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(int categoryId, Category category) {
        Category updatedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException());
        if(category.getName()!=null){updatedCategory.setName(category.getName());}
        if(category.getAgeLimit()>0){updatedCategory.setAgeLimit(category.getAgeLimit());}
        return categoryRepo.save(updatedCategory);
    }

    @Override
    public void deleteCategory(int id) {
        if(!categoryRepo.existsById(id)){throw new ResourceNotFoundException();}
        categoryRepo.deleteById(id);
    }
}
