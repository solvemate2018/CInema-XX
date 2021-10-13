package com.future.cinemaxx.services.genres;

import com.future.cinemaxx.entities.Genre;

import java.util.List;

public interface GenreServiceInterface {
    List<Genre> getAllGenres();
    Genre getGenreById(int id);
    Genre createGenre(Genre genre);
    void deleteGenre(int genreId);
}
