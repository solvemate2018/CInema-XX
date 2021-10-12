package com.future.cinemaxx.services.theater;

import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public class TheaterServiceImpl implements com.future.cinemaxx.services.theater.TheaterServiceInterface {

    TheaterRepo theaterRepo;

    public TheaterServiceImpl(TheaterRepo theaterRepo)
    {
        this.theaterRepo=theaterRepo;
    }

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepo.findAll();
    }

    @Override
    public Theater getTheaterByID(int id) {
        return theaterRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Theater createTheater(Theater theater, String name, String city, String street, int streetNumber) {
        return theaterRepo.save(new Theater(theater.getName(),
                theater.getCity(),
                theater.getStreet(),
                theater.getStreetNumber()));

    }

    @Override
    public void deleteTheater(int theaterId) {
        if(!theaterRepo.existsById(theaterId)) throw new ResourceNotFoundException();
        theaterRepo.deleteById(theaterId);
    }
}
