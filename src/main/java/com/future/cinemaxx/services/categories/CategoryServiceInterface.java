package com.future.cinemaxx.services.categories;

import com.future.cinemaxx.entities.Category;

import java.util.List;

public interface CategoryServiceInterface {
    List<Category> getAllCategories();
    Category getById(int id);
    Category create(Category category);
    Category updateCategory(int categoryId, Category category);
    void deleteCategory(int id);
}
