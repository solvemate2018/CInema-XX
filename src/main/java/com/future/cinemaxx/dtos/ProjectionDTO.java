package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectionDTO {
    private LocalDateTime startTime;
    private float ticketPrice;
    private CinemaHallDTO hall;
    private MovieDTO movie;
}
