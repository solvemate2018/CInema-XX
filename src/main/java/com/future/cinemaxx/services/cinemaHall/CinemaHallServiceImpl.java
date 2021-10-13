package com.future.cinemaxx.services.cinemaHall;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CinemaHallServiceImpl implements CinemaHallServiceInterface {

    CinemaHallRepo cinemaHallRepo;

    public CinemaHallServiceImpl(CinemaHallRepo cinemaHallRepo)
    {
        this.cinemaHallRepo=cinemaHallRepo;
    }

    @Override
    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepo.findAll();
    }

    @Override
    public CinemaHall getCinemaHallById(int id) {
        return cinemaHallRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public CinemaHall createCinemaHall(CinemaHall cinemaHall, String name, int numberOfRows, int numberOfColumns) {
        return null;
    }

    @Override
    public void deleteCinemaHall(int CinemaHallId) {

    }
}
