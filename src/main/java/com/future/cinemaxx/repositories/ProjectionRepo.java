package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionRepo extends JpaRepository<Projection, Integer> {
    List<Projection> getProjectionByHall_Theater_Id(int id);
    List<Projection> getProjectionByHall_Theater_IdAndStartTime(int id, LocalDate date); ///

    //Fix for a LocalDate implementation
    List<Projection> getProjectionsByStartTimeBetween(LocalDateTime date1,LocalDateTime date2);
}
