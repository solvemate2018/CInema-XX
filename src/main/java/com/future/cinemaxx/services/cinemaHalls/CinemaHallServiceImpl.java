package com.future.cinemaxx.services.cinemaHalls;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
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
        return cinemaHallRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is not such Cinema Hall"));
    }

    @Override
    public CinemaHall createCinemaHall(CinemaHall cinemaHall, int theaterId) {
        if(cinemaHall.getName().isEmpty() || cinemaHall.getName().isBlank() || cinemaHall.getName() == null){
            throw new IllegalArgumentException("The name cannot be blank or empty");
        }
        if(cinemaHall.getNumberOfColumns() < 2 || cinemaHall.getNumberOfColumns() > 25 || cinemaHall.getNumberOfRows() < 2 || cinemaHall.getNumberOfRows() > 25)
            throw new IllegalArgumentException("The seat number or column number should not be less than 2 or more than 25!");
        return cinemaHallRepo.save(new CinemaHall(cinemaHall.getName(), cinemaHall.getNumberOfRows(),
                cinemaHall.getNumberOfColumns(),
                theaterRepo.findById(theaterId).orElseThrow(() -> new ResourceNotFoundException("There is not such theater in our system."))));
    }

    @Override
    public void deleteCinemaHall(int cinemaHallId) {
        if (!cinemaHallRepo.existsById(cinemaHallId)) throw new ResourceNotFoundException("There is not such Cinema Hall");
        cinemaHallRepo.deleteById(cinemaHallId);
    }
}
