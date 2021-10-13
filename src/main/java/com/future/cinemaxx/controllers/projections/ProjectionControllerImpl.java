package com.future.cinemaxx.controllers.projections;

import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.projections.ProjectionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projections")
public class ProjectionControllerImpl implements ProjectionControllerInterface {

    ProjectionServiceInterface projectionService;
    DTOConverter dtoConverter;

    @Autowired
    public ProjectionControllerImpl(ProjectionServiceInterface projectionService, DTOConverter dtoConverter){
        this.projectionService=projectionService;
        this.dtoConverter=dtoConverter;
    }

    @Override
    public List<ProjectionDTO> getAll() {
        return dtoConverter.convertToListProjectionDTO(projectionService.getAll());
    }

    @Override
    public ProjectionDTO getById(int id) {
        return dtoConverter.convertToProjectionDTO(
                projectionService.getById(id));
    }

    @Override
    public List<ProjectionDTO> getByTheaterId(int theaterId) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByTheaterId(theaterId));
    }

    @Override
    public List<ProjectionDTO> getByTheaterIdAndHall(int theaterId, int hallId) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByTheaterIdAndHall(theaterId,hallId));
    }

    @Override
    public List<ProjectionDTO> getByDateAndTheaterId(int theaterId, LocalDate time) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByDateAndTheaterId(theaterId,time));
    }

    @Override
    public List<ProjectionDTO> getProjectionsBetweenDates(int theaterId, LocalDate date1, LocalDate date2) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getProjectionsBetweenDates(theaterId, date1 ,date2));
    }
}
