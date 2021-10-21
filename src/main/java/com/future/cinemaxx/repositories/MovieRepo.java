package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {
    Movie findMovieByName(String name);
    boolean existsByCategory(Category category);
    boolean existsByGenre(Genre genre);
}
