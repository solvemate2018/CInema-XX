package com.future.cinemaxx.services.genres;

import com.future.cinemaxx.entities.Genre;
import com.future.cinemaxx.repositories.GenreRepo;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GenreServiceImpl implements GenreServiceInterface {
    GenreRepo genreRepo;

    public GenreServiceImpl(GenreRepo genreRepo) {
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
        if (genreRepo.existsByName(genre.getName())) {
            throw new IllegalArgumentException("A category with name: " + genre.getName() + " already exists");
        }
        return genreRepo.save(genre);
    }

    @Override
    public Genre updateGenre(int genreId, Genre genre) {
        Genre updatedGenre = genreRepo.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException());
        if (genre.getName() != null) {
            updatedGenre.setName(genre.getName());
        }
        return genreRepo.save(updatedGenre);
    }

    @Override
    public void deleteGenre(int genreId) {
        if (!genreRepo.existsById(genreId)) {
            throw new ResourceNotFoundException();
        }
        genreRepo.deleteById(genreId);
    }
}
