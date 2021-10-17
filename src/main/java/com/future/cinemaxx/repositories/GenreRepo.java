package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Integer> {
    Genre findGenreByName(String name);
    boolean existsByName(String name);
}
