package com.future.cinemaxx.rest.theater;

import com.future.cinemaxx.dtos.TheaterDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.theaters.TheaterServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/theater")
public class TheaterControllerImpl implements TheaterControllerInterface {

    TheaterServiceInterface theaterService;
    DTOConverter dtoConverter;

    @Autowired
    public TheaterControllerImpl(TheaterServiceInterface theaterService, DTOConverter dtoConverter){
        this.theaterService = theaterService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<TheaterDTO> getAll() {
        return theaterService.getAllTheaters().stream().map(theater -> dtoConverter.convertToTheaterDTO(theater)).collect(Collectors.toList());
    }

    @Override
    public TheaterDTO getTheaterById(int id) {
        return dtoConverter.convertToTheaterDTO(theaterService.getTheaterById(id));
    }

    @Override
    public TheaterDTO createTheater(TheaterDTO theaterDTO) {
        return dtoConverter.convertToTheaterDTO(theaterService.createTheater(dtoConverter.convertToTheater(theaterDTO)));
    }

    @Override
    public void delete(int id) {
        theaterService.deleteTheater(id);
    }

    @Override
    public TheaterDTO updateTheater(int id, TheaterDTO theater) {
        return dtoConverter.convertToTheaterDTO(theaterService.updateTheater(id,dtoConverter.convertToTheater(theater)));
    }

    @Override
    public TheaterDTO addCinemaHall(int projectionId, int hallId) {
        return dtoConverter.convertToTheaterDTO(theaterService.addCinemaHall(projectionId,hallId));
    }
}
