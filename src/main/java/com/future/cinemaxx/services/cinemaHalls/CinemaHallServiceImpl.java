package com.future.cinemaxx.services.cinemaHalls;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CinemaHallServiceImpl implements CinemaHallServiceInterface {

    CinemaHallRepo cinemaHallRepo;
    TheaterRepo theaterRepo;


    public CinemaHallServiceImpl(CinemaHallRepo cinemaHallRepo) {
        this.cinemaHallRepo = cinemaHallRepo;
    }

    @Override
    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepo.findAll();
    }

    @Override
    public CinemaHall getCinemaHallById(int id) {
        return cinemaHallRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CinemaHall createCinemaHall(CinemaHall cinemaHall, String name, int numberOfRows, int numberOfColumns, int theaterId) {
        return cinemaHallRepo.save(new CinemaHall(cinemaHall.getName(), cinemaHall.getNumberOfRows(),
                cinemaHall.getNumberOfColumns(),
                theaterRepo.findById(theaterId).orElseThrow(() -> new ResourceNotFoundException())));


    }

    @Override
    public void deleteCinemaHall(int cinemaHallId) {
        if (!cinemaHallRepo.existsById(cinemaHallId)) throw new ResourceNotFoundException();
        cinemaHallRepo.deleteById(cinemaHallId);

    }
}
