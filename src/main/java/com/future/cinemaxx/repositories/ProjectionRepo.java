package com.future.cinemaxx.repositories;

import com.future.cinemaxx.entities.Movie;
import com.future.cinemaxx.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectionRepo extends JpaRepository<Projection, Integer> {

    List<Projection> getProjectionsByHall_Theater_Id(int id);

    List<Projection> getProjectionsByHall_Theater_IdAndStartTimeBetween(int id, LocalDateTime date1,LocalDateTime date2);

    List<Projection> getProjectionsByHall_Theater_IdAndHall_Name(int theaterId, String hallName);

    List<Projection> getProjectionByHall_Id(int hallId);

    Boolean existsByMovie(Movie movie);
}
