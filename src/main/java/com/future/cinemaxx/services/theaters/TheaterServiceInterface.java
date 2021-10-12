package com.future.cinemaxx.services.theaters;

import com.future.cinemaxx.dtos.TheaterDTO;
import com.future.cinemaxx.entities.Theater;

import java.util.List;

public interface TheaterServiceInterface {
    List<Theater> getAllTheaters();
    Theater getTheaterById(int id);
    Theater createTheater(Theater theater);
    void deleteTheater(int id);

}
