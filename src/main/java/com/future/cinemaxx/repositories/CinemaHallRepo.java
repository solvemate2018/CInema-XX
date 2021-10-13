package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaHallRepo extends JpaRepository<CinemaHall, Integer> {
    CinemaHall findCinemaHallByName(String name);
}
