package com.future.cinemaxx.rest.projection;

import com.future.cinemaxx.dtos.ProjectionDTO;
import com.future.cinemaxx.entities.Projection;
import com.future.cinemaxx.services.projections.ProjectionServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projection/")
public class ProjectionControllerImpl implements ProjectionControllerInterface{

    @Autowired
    ProjectionServiceInterface projectionService;
    @Autowired
    ModelMapper modelMapper;

    private ProjectionDTO convertToDTO(Projection projection){
        return modelMapper.map(projection,ProjectionDTO.class);
    }

    private Projection convertToEntity(ProjectionDTO projectionDTO){
        return modelMapper.map(projectionDTO, Projection.class);
    }

    @Override
    public void deleteProjectionById(int id) {
        projectionService.deleteProjection(id);
    }

    @Override
    public void updateProjection(int id, ProjectionDTO projectionDTO) {
    projectionService.updateProjectionById(id, convertToEntity(projectionDTO));
    }
}
