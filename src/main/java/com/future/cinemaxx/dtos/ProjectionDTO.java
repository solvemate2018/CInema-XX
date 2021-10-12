package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.cinemaxx.entities.CinemaHall;
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
//    CinemaHallDTO hall; Or maybe just cinema hall name and id.
    private MovieDTO movie;
}
