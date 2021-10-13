
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
public class TheaterDTO {
    private String name;
    private String city;
    private String street;
    private int streetNumber;
    private List<CinemaHallDTO> cinemaHallDTOList;
}