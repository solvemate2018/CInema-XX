package com.future.cinemaxx.services.theaters;

import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Theater;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.TheaterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterServiceInterface {
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
        return theaterRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no such theater in our system"));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Theater createTheater(Theater theater) {
        if(theater.getName().isBlank() || theater.getName().isEmpty() || theater.getName() == null){
            throw new IllegalArgumentException("The name must not be empty");
        }
        else if(theater.getStreet().isBlank() || theater.getStreet().isEmpty() || theater.getStreet() == null){
            throw new IllegalArgumentException("The street must not be empty");
        }
        else if(theater.getCity().isBlank() || theater.getCity().isEmpty() || theater.getCity() == null){
            throw new IllegalArgumentException("The city must not be empty");
        }
        else if(theater.getStreetNumber() <= 0){
            throw new IllegalArgumentException("The street number must not be negative or 0");
        }
        return theaterRepo.save(theater);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteTheater(int id) {
        if (!hallRepo.existsByTheater(theaterRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no such theater in our system!")))) {
            theaterRepo.deleteById(id);
        }
        throw new IllegalStateException("There are Cinema halls in this Theater. Try deleting them first!");
    }

    @Override
    public Theater updateTheater(int theaterId, Theater theater) {
        Theater updatedTheater = theaterRepo.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException());
        if(theater.getName().isBlank() || theater.getName().isEmpty()){
            throw new IllegalArgumentException("The name must not be empty");
        }
        else if(theater.getStreet().isBlank() || theater.getStreet().isEmpty()){
            throw new IllegalArgumentException("The street must not be empty");
        }
        else if(theater.getCity().isBlank() || theater.getCity().isEmpty()){
            throw new IllegalArgumentException("The city must not be empty");
        }
        else if(theater.getStreetNumber() <= 0){
            throw new IllegalArgumentException("The street number must not be negative or 0");
        }
        if (theater.getName() != null)
        updatedTheater.setName(theater.getName());
        if (theater.getCity() != null)
        updatedTheater.setCity(theater.getCity());
        if (theater.getStreet() != null)
        updatedTheater.setStreet(theater.getStreet());
        if (theater.getStreetNumber() != 0)
        updatedTheater.setStreetNumber(theater.getStreetNumber());
        return theaterRepo.save(updatedTheater);
    }

    @Override
    public Theater addCinemaHall(int projectionId, int hallId) {
        CinemaHall hall = hallRepo.findById(hallId).orElseThrow(()->new ResourceNotFoundException("There is no such cinema hall in our system!"));
        Theater theater = theaterRepo.findById(projectionId).orElseThrow(()->new ResourceNotFoundException("There is no such theater in our system!"));
        theater.getCinemaHalls().add(hall);
        return theaterRepo.save(theater);
    }
}
