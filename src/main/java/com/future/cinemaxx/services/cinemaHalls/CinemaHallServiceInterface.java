package com.future.cinemaxx.services.cinemaHalls;

import com.future.cinemaxx.entities.CinemaHall;

import java.util.List;

public interface CinemaHallServiceInterface{
    List<CinemaHall> getAllCinemaHalls();
    CinemaHall getCinemaHallById(int id);
    CinemaHall createCinemaHall(CinemaHall cinemaHall, int theaterId);
    void deleteCinemaHall (int CinemaHallId);
}
