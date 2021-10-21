package com.future.cinemaxx.rest.cinemaHall;

import com.future.cinemaxx.dtos.CinemaHallDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.categories.CategoryServiceImpl;
import com.future.cinemaxx.services.cinemaHalls.CinemaHallServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/cinema_hall")
public class CinemaHallControllerImpl implements CinemaHallControllerInterface{
    @Autowired
    CinemaHallServiceImpl cinemaHallService;
    DTOConverter dtoConverter;

    @Autowired
    public CinemaHallControllerImpl(CinemaHallServiceImpl cinemaHallService, DTOConverter dtoConverter) {
        this.cinemaHallService = cinemaHallService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<CinemaHallDTO> getAll() {
        return cinemaHallService.getAllCinemaHalls().stream()
                .map(cinemaHall -> dtoConverter.convertToCinemaHallDTO(cinemaHall)).collect(Collectors.toList());
    }

    @Override
    public CinemaHallDTO getById(int id) {
        return dtoConverter.convertToCinemaHallDTO(cinemaHallService.getCinemaHallById(id));
    }

    @Override
    public CinemaHallDTO create(CinemaHallDTO cinemaHallDTO, int theaterId) {
        return dtoConverter.convertToCinemaHallDTO(cinemaHallService.createCinemaHall(dtoConverter.convertToCinemaHall(cinemaHallDTO), theaterId));
    }

    @Override
    public void deleteById(int id) {
        cinemaHallService.deleteCinemaHall(id);
    }
}
