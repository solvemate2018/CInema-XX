package com.future.cinemaxx.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.cinemaxx.entities.Category;
import com.future.cinemaxx.entities.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {
    private String name;
    private Duration duration;
    private CategoryDTO category;
    private GenreDTO genre;
}
