package com.future.cinemaxx.rest.projection;

import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.projections.ProjectionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projection")
public class ProjectionControllerImpl implements ProjectionControllerInterface {

    ProjectionServiceInterface projectionService;
    DTOConverter dtoConverter;

    @Autowired
    public ProjectionControllerImpl(ProjectionServiceInterface projectionService, DTOConverter dtoConverter) {
        this.projectionService = projectionService;
        this.dtoConverter = dtoConverter;
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
    public List<ProjectionDTO> getByHallId(int hallId) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByHallId(hallId));
    }

    @Override
    public List<ProjectionDTO> getByTheaterIdAndHallName(int theaterId, String hallName) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByTheaterIdAndHallName(theaterId, hallName));
    }

    @Override
    public List<ProjectionDTO> getByDateAndTheaterId(int theaterId, LocalDate date) {
        return dtoConverter.convertToListProjectionDTO(projectionService.getByDateAndTheaterId(theaterId, date));
    }

    @Override
    public List<ProjectionDTO> getProjectionsBetweenDates(int theaterId, LocalDate dateFrom, LocalDate dateTo) {
        return dtoConverter.convertToListProjectionDTO(projectionService
                .getProjectionsBetweenDates(theaterId, dateFrom, dateTo));
    }

    @Override
    public void deleteProjectionById(int id) {
        projectionService.deleteProjection(id);
    }

    @Override
    public ProjectionDTO updateProjection(int id, int movieId, int hallId, ProjectionDTO projectionDTO) {
        return dtoConverter.convertToProjectionDTO(projectionService.
                updateProjectionById(id, movieId, hallId, dtoConverter.convertToProjection(projectionDTO)));
    }



    @Override
    public ProjectionDTO createProjection(int movieId, int hallId, ProjectionDTO projectionDTO) {
        return dtoConverter.convertToProjectionDTO(projectionService.createProjection(dtoConverter.convertToProjection(projectionDTO),
                movieId, hallId));
    }


}
