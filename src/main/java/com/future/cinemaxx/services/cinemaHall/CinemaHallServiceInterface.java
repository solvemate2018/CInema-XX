package com.future.cinemaxx.services.cinemaHall;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Movie;

import java.util.List;

public interface CinemaHallServiceInterface {

    List<CinemaHall> getAllCinemaHalls();
    CinemaHall getCinemaHallById(int id);
    CinemaHall createCinemaHall(CinemaHall cinemaHall, String name, int numberOfRows, int numberOfColumns, int theaterId);
    void deleteCinemaHall (int CinemaHallId);


}
