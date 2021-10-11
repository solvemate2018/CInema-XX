package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Integer> {
}
