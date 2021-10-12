package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectionRepo extends JpaRepository<Projection, Integer> {
    List<Projection> getProjectionByHall_Theater_Id(int id);
}
