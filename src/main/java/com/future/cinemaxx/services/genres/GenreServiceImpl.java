package com.future.cinemaxx.services.genres;

import com.future.cinemaxx.entities.CinemaHall;
import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.repositories.CinemaHallRepo;
import com.future.cinemaxx.repositories.GenreRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreServiceInterface{
    GenreRepo genreRepo;

    public GenreServiceImpl(GenreRepo genreRepo)
    {
        this.genreRepo = genreRepo;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    @Override
    public Genre getGenreById(int id) {
        return genreRepo.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepo.save(genre);
    }

    @Override
    public void deleteGenre(int genreId) {
        genreRepo.deleteById(genreId);
    }
}