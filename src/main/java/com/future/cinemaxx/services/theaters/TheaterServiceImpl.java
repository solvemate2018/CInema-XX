package com.future.cinemaxx.services.theaters;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterServiceInterface{
    @Autowired
    TheaterRepo theaterRepo;
    @Autowired
    CinemaHallRepo hallRepo;

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepo.findAll();
    }

    @Override
    public Theater getTheaterById(int id) {
        return theaterRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Theater createTheater(Theater theater) {
        return theaterRepo.save(theater);
    }

    @Override
    public void deleteTheater(int id) {
        if(theaterRepo.existsById(id)) {
            theaterRepo.deleteById(id);
        }else{
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public Theater updateTheater(int theaterId, Theater theater) {
        Theater updatedTheater = theaterRepo.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException());
        if(theater.getName()!=null){updatedTheater.setName(theater.getName());}
        if(theater.getCity()!=null){updatedTheater.setCity(theater.getCity());}
        if(theater.getStreet()!=null){updatedTheater.setStreet(theater.getStreet());}
        if(theater.getStreetNumber()>0){updatedTheater.setStreetNumber(theater.getStreetNumber());}
        return theaterRepo.save(updatedTheater);
    }

    @Override
    public Theater addCinemaHall(int projectionId, int hallId) {
        CinemaHall hall = hallRepo.findById(hallId).orElseThrow(()->new ResourceNotFoundException());
        Theater theater = theaterRepo.findById(projectionId).orElseThrow(()->new ResourceNotFoundException());
        theater.getCinemaHalls().add(hall);
        return theaterRepo.save(theater);
    }
}
