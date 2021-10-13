package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectionRepo extends JpaRepository<Projection, Integer> {
    List<Projection> getProjectionByHall_Theater_Id(int id);
    List<Projection> getProjectionByHall_Theater_IdAndStartTime(int id, LocalDate date); ///

    //Fix for a LocalDate implementation
    List<Projection> getProjectionsByStartTimeBetween(LocalDateTime date1,LocalDateTime date2);
}
