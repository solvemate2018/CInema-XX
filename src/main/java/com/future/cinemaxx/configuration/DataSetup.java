package com.future.cinemaxx.configuration;

import com.future.cinemaxx.entities.*;
import com.future.cinemaxx.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class DataSetup implements CommandLineRunner {
    CategoryRepo categoryRepo;
    CinemaHallRepo cinemaHallRepo;
    GenreRepo genreRepo;
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    TheaterRepo theaterRepo;
    TicketRepo ticketRepo;

    public DataSetup(CategoryRepo categoryRepo,
                     CinemaHallRepo cinemaHallRepo,
                     GenreRepo genreRepo,
                     MovieRepo movieRepo,
                     ProjectionRepo projectionRepo,
                     TheaterRepo theaterRepo,
                     TicketRepo ticketRepo){
        this.categoryRepo = categoryRepo;
        this.cinemaHallRepo = cinemaHallRepo;
        this.genreRepo = genreRepo;
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.theaterRepo = theaterRepo;
        this.ticketRepo = ticketRepo;
    }

    @Override
    public void run(String... args) {
        Theater theater = theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25));
        CinemaHall cinemaHall1 = cinemaHallRepo.save(new CinemaHall("A", 12, 16, theater));
        CinemaHall cinemaHall2 = cinemaHallRepo.save(new CinemaHall("B", 8, 12, theater));
        CinemaHall cinemaHall3 = cinemaHallRepo.save(new CinemaHall("C", 10, 14, theater));

        Category category1 = categoryRepo.save(new Category("A", 8));
        Category category2 = categoryRepo.save(new Category("B", 12));
        Category category3 = categoryRepo.save(new Category("C", 16));
        Category category4 = categoryRepo.save(new Category("D", 18));

        Genre genre1 = genreRepo.save(new Genre("Horror"));
        Genre genre2 = genreRepo.save(new Genre("Comedy"));
        Genre genre3 = genreRepo.save(new Genre("Drama"));
        Genre genre4 = genreRepo.save(new Genre("Fantasy"));

        Movie movie1 = movieRepo.save(new Movie("Scary movie",Duration.ofMinutes(125) , genre2, category4));
        Movie movie2 = movieRepo.save(new Movie("Spider-man", Duration.ofMinutes(140), genre4, category1));
        Movie movie3 = movieRepo.save(new Movie("Gladiator",Duration.ofMinutes(115) , genre1, category3));
        Movie movie4 = movieRepo.save(new Movie("Last movie",Duration.ofMinutes(163) , genre3, category2));

        Projection projection1 = projectionRepo.save(new Projection(LocalDateTime.now(), 15.6f, cinemaHall1, movie1));
        Projection projection2 = projectionRepo.save(new Projection(LocalDateTime.now(), 15.6f, cinemaHall1, movie2));
        Projection projection3 = projectionRepo.save(new Projection(LocalDateTime.now(), 15.6f, cinemaHall1, movie4));
        Projection projection4 = projectionRepo.save(new Projection(LocalDateTime.now(), 15.6f, cinemaHall1, movie3));
        Projection projection5 = projectionRepo.save(new Projection(LocalDateTime.now(), 15.6f, cinemaHall1, movie2));
    }
}
