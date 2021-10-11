package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepo extends JpaRepository<Theater, Integer> {
}
