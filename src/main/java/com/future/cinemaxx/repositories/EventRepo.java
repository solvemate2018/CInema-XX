package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Integer> {
}
