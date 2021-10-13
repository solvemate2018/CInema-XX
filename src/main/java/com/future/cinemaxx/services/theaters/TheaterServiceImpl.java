package com.future.cinemaxx.services.theaters;

import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public class TheaterServiceImpl implements TheaterServiceInterface{
    @Autowired
    TheaterRepo theaterRepo;

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepo.findAll();
    }

    @Override
    public Theater getTheaterById(int id) {
        return theaterRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    } //.

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
}
