package com.future.cinemaxx.services.imdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.future.cinemaxx.dtos.MovieDetails;

import java.util.List;

public interface ImdbMovieServiceInteface {
    List<String> getImagesForImdbId(String imdbId);
    String getTrailerForImdbId(String imdbId);
    List<String> getPostersForImdbId(String imdbId);
    MovieDetails getMovieDetails(int movieId) throws JsonProcessingException;
    String getImdbIdFromMovieId(int movieId);
}
