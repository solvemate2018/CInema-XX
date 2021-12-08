package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {
    private int id;
    private String name;
    private int durationInMinutes;
    private CategoryDTO category;
    private GenreDTO genre;
}
