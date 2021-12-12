package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaHallDTO {
    private String id;
    private String name;
    private int numberOfRows;
    private int numberOfColumns;
    private List<ProjectionDTO> projectionDTOS;
}
