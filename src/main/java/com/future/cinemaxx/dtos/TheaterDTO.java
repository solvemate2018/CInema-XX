package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.cinemaxx.entities.CinemaHall;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterDTO {
    private String name;
    private String city;
    private String street;
    private int streetNumber;
    // List<CinemaHallDTO> cinemaHallDTOs = new ArrayList<>(); idk if its needed here?
}
