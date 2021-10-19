package com.future.cinemaxx.services.theaters;

import com.future.cinemaxx.entities.Theater;
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

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Theater> getAllTheaters() {
        return theaterRepo.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Theater getTheaterById(int id) {
        return theaterRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Theater createTheater(Theater theater) {
        return theaterRepo.save(theater);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteTheater(int id) {
        if (theaterRepo.existsById(id)) {
            theaterRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
