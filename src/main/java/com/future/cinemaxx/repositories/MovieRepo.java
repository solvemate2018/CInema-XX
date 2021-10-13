package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
}
