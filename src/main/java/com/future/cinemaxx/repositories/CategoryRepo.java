package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String name);
}
