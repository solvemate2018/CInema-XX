package com.future.cinemaxx.rest.genre;

import com.future.cinemaxx.dtos.CategoryDTO;
import com.future.cinemaxx.dtos.GenreDTO;
import com.future.cinemaxx.dtos.converter.DTOConverter;
import com.future.cinemaxx.services.genres.GenreServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/genre")
public class GenreControllerImpl implements GenreControllerInterface {

    @Autowired
    GenreServiceInterface genreService;
    @Autowired
    DTOConverter dtoConverter;

    public GenreControllerImpl(GenreServiceInterface genreService, DTOConverter dtoConverter){
        this.genreService = genreService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public List<GenreDTO> getAll() {
        return genreService.getAllGenres().stream().map(genre -> dtoConverter.convertToGenreDTO(genre)).collect(Collectors.toList());
    }

    @Override
    public GenreDTO getById(int id) {
        return dtoConverter.convertToGenreDTO(genreService.getGenreById(id));
    }

    @Override
    public GenreDTO create(GenreDTO genreDTO) {
        return dtoConverter.convertToGenreDTO(genreService.createGenre(dtoConverter.convertToGenre(genreDTO)));

    }

    @Override
    public void delete(int id) {
        genreService.deleteGenre(id);

    }

    @Override
    public GenreDTO update(int id, GenreDTO genreDTO) {
        return dtoConverter.convertToGenreDTO(genreService.updateGenre(id, dtoConverter.convertToGenre(genreDTO)));
    }
}
