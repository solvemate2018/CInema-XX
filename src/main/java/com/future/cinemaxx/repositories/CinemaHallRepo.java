package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaHallRepo extends JpaRepository<CinemaHall, Integer> {
}
