package com.future.cinemaxx.services.theater;

import com.future.cinemaxx.entities.Theater;

import java.util.List;

public interface TheaterServiceInterface {

    List<Theater> getAllTheaters();
    Theater getTheaterByID (int id);
    Theater createTheater (Theater theater, String name, String city, String street, int streetNumber);
    void deleteTheater (int theaterId);
}
