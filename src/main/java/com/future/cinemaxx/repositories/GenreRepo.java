package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Integer> {
    Genre findGenreByName(String name);
}
