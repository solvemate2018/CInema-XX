package com.future.cinemaxx.dtos;

import java.time.LocalDate;
import java.util.List;

public class MovieDetails {
    private String title;
    private String fullTitle;
    private LocalDate releaseDate;
    private double imDbRating;
    private String runtimeStr;
    private List<ActorsInfo> actorList;
}
