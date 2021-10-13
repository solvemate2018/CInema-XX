package com.future.cinemaxx.services.categories;

import com.future.cinemaxx.entities.Category;

import java.util.List;

public interface CategoryServiceInterface {
    List<Category> getAllCategories();
    Category addCategory(String name, int ageLimit);
    void deleteCategory(int id);
}
