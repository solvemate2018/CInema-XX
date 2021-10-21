package com.future.cinemaxx.testUtils;

import com.future.cinemaxx.entities.*;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.security.repositories.RoleRepository;
import com.future.cinemaxx.security.repositories.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDataMaker {

    public static void clear(TheaterRepo theaterRepo, CinemaHallRepo cinemaHallRepo, CategoryRepo categoryRepo,
                             GenreRepo genreRepo, MovieRepo movieRepo, ProjectionRepo projectionRepo,
                             TicketRepo ticketRepo, UserRepository userRepository, RoleRepository roleRepository) {
        ticketRepo.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        projectionRepo.deleteAll();
        movieRepo.deleteAll();
        categoryRepo.deleteAll();
        genreRepo.deleteAll();
        cinemaHallRepo.deleteAll();
        theaterRepo.deleteAll();
    }

    public static ArrayList<Integer>[] makeDataForTests(TheaterRepo theaterRepo, CinemaHallRepo cinemaHallRepo, CategoryRepo categoryRepo,
                                                        GenreRepo genreRepo, MovieRepo movieRepo, ProjectionRepo projectionRepo, TicketRepo ticketRepo) {
        ticketRepo.deleteAll();
        projectionRepo.deleteAll();
        movieRepo.deleteAll();
        categoryRepo.deleteAll();
        genreRepo.deleteAll();
        cinemaHallRepo.deleteAll();
        theaterRepo.deleteAll();


        ArrayList<Integer>[] ids = new ArrayList[4];

        ArrayList<Integer> theaterIds = new ArrayList<>();

        Theater theater0 = theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25));
        Theater theater1 = theaterRepo.save(new Theater("Whatever", "Copenhagen", "Test", 11));

        theaterIds.add(theater0.getId());
        theaterIds.add(theater1.getId());

        ArrayList<Integer> hallIds = new ArrayList<Integer>();

        CinemaHall cinemaHall1 = cinemaHallRepo.save(new CinemaHall("A", 12, 16, theater0));
        CinemaHall cinemaHall2 = cinemaHallRepo.save(new CinemaHall("B", 8, 12, theater0));
        CinemaHall cinemaHall3 = cinemaHallRepo.save(new CinemaHall("C", 10, 14, theater0));
        CinemaHall cinemaHall4 = cinemaHallRepo.save(new CinemaHall("A", 7, 21, theater1));
        CinemaHall cinemaHall5 = cinemaHallRepo.save(new CinemaHall("B", 15, 4, theater1));
        CinemaHall cinemaHall6 = cinemaHallRepo.save(new CinemaHall("C", 9, 14, theater1));

        hallIds.add(cinemaHall1.getId());
        hallIds.add(cinemaHall2.getId());
        hallIds.add(cinemaHall3.getId());
        hallIds.add(cinemaHall4.getId());
        hallIds.add(cinemaHall5.getId());
        hallIds.add(cinemaHall6.getId());

        Category category1 = categoryRepo.save(new Category("A", 8));
        Category category2 = categoryRepo.save(new Category("B", 12));
        Category category3 = categoryRepo.save(new Category("C", 16));
        Category category4 = categoryRepo.save(new Category("D", 18));

        Genre genre1 = genreRepo.save(new Genre("Horror"));
        Genre genre2 = genreRepo.save(new Genre("Comedy"));
        Genre genre3 = genreRepo.save(new Genre("Drama"));
        Genre genre4 = genreRepo.save(new Genre("Fantasy"));

        ArrayList<Integer> movieIds = new ArrayList<Integer>();

        Movie movie1 = movieRepo.save(new Movie("Scary movie", Duration.ofMinutes(125), genre2, category4));
        Movie movie2 = movieRepo.save(new Movie("Spider-man", Duration.ofMinutes(140), genre4, category1));
        Movie movie3 = movieRepo.save(new Movie("Gladiator", Duration.ofMinutes(115), genre1, category3));
        Movie movie4 = movieRepo.save(new Movie("Last movie", Duration.ofMinutes(163), genre3, category2));
        Movie movie5 = movieRepo.save(new Movie("Inception", Duration.ofMinutes(200), genre3, category3));
        Movie movie6 = movieRepo.save(new Movie("Dark Knight", Duration.ofMinutes(220), genre3, category3));
        Movie movie7 = movieRepo.save(new Movie("Deadpool", Duration.ofMinutes(163), genre2, category4));

        movieIds.add(movie1.getId());
        movieIds.add(movie2.getId());
        movieIds.add(movie3.getId());
        movieIds.add(movie4.getId());
        movieIds.add(movie5.getId());
        movieIds.add(movie6.getId());
        movieIds.add(movie7.getId());

        ArrayList<Integer> projectionsIds = new ArrayList<>();

        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.parse("2021-11-27T12:30:00"), 15.6f, cinemaHall1, movie1)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(7), 15.6f, cinemaHall1, movie2)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall3, movie4)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall2, movie3)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(1).plusHours(2), 15.6f, cinemaHall4, movie2)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(100).plusHours(2), 15.6f, cinemaHall4, movie2)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(22).plusHours(12), 15.6f, cinemaHall4, movie2)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(22), 15.6f, cinemaHall1, movie4)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(11), 15.6f, cinemaHall2, movie4)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(5), 15.6f, cinemaHall3, movie4)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(5), 15.6f, cinemaHall3, movie4)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(9), 15.6f, cinemaHall5, movie5)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(12), 15.6f, cinemaHall5, movie5)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(13), 15.6f, cinemaHall5, movie5)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-12T12:30:00"), 15.6f, cinemaHall5, movie6)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-14T11:35:00"), 15.6f, cinemaHall5, movie6)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-14T09:35:00"), 15.6f, cinemaHall4, movie7)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall5, movie7)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall6, movie7)).getId());
        projectionsIds.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(3), 15.6f, cinemaHall1, movie7)).getId());
        ids[0] = theaterIds;
        ids[1] = hallIds;
        ids[2] = movieIds;
        ids[3] = projectionsIds;
        return ids;
    }

    public static ArrayList<Integer> setUpCategories(CategoryRepo categoryRepo) {
        categoryRepo.deleteAll();
        ArrayList<Integer> categories = new ArrayList<Integer>();
        categories.add(categoryRepo.save(new Category("A", 8)).getId());
        categories.add(categoryRepo.save(new Category("B", 12)).getId());
        categories.add(categoryRepo.save(new Category("C", 16)).getId());
        categories.add(categoryRepo.save(new Category("D", 18)).getId());
        return categories;
    }

    public static ArrayList<Integer> setUpGenres(GenreRepo genreRepo) {
        genreRepo.deleteAll();
        ArrayList<Integer> genres = new ArrayList<Integer>();
        genres.add(genreRepo.save(new Genre("Horror")).getId());
        genres.add(genreRepo.save(new Genre("Comedy")).getId());
        genres.add(genreRepo.save(new Genre("Drama")).getId());
        genres.add(genreRepo.save(new Genre("Fantasy")).getId());
        return genres;
    }

    public static ArrayList<Integer> setUpTickets(TheaterRepo theaterRepo, CinemaHallRepo cinemaHallRepo, CategoryRepo categoryRepo,
                                                  GenreRepo genreRepo, MovieRepo movieRepo, ProjectionRepo projectionRepo,
                                                  TicketRepo ticketRepo) {
        ticketRepo.deleteAll();
        projectionRepo.deleteAll();
        movieRepo.deleteAll();
        categoryRepo.deleteAll();
        genreRepo.deleteAll();
        cinemaHallRepo.deleteAll();
        theaterRepo.deleteAll();

        ArrayList<Projection> projections = new ArrayList<Projection>();
        ArrayList<Integer> projectionIds = new ArrayList<Integer>();

        Theater theater0 = theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25));

        CinemaHall cinemaHall1 = cinemaHallRepo.save(new CinemaHall("A", 12, 16, theater0));
        CinemaHall cinemaHall2 = cinemaHallRepo.save(new CinemaHall("B", 8, 12, theater0));

        Category category1 = categoryRepo.save(new Category("A", 8));
        Category category2 = categoryRepo.save(new Category("R", 18));

        Genre genre1 = genreRepo.save(new Genre("Horror"));
        Genre genre2 = genreRepo.save(new Genre("Comedy"));

        Movie movie1 = movieRepo.save(new Movie("Halloween", Duration.ofMinutes(232), genre1, category2));
        Movie movie2 = movieRepo.save(new Movie("Nightmare On Elm Street", Duration.ofMinutes(115), genre1, category2));
        Movie movie3 = movieRepo.save(new Movie("Up", Duration.ofMinutes(163), genre2, category1));

        projections.add(projectionRepo.save(new Projection(LocalDateTime.parse("2021-11-27T12:30:00"), 15.6f, cinemaHall1, movie1)));
        projections.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall2, movie3)));
        projections.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall2, movie2)));
        projections.add(projectionRepo.save(new Projection(LocalDateTime.now().plusDays(22).plusHours(10), 15.6f, cinemaHall2, movie2)));

        for(int i = 0; i<projections.size(); i++){
            projectionIds.add(projections.get(i).getId());
            ticketRepo.saveAll(projections.get(i).getTickets());
        }
        return projectionIds;
    }
    public static ArrayList<Integer>[] setUpTheatersAndHalls(TheaterRepo theaterRepo, CinemaHallRepo cinemaHallRepo){
        cinemaHallRepo.deleteAll();
        theaterRepo.deleteAll();
        ArrayList<Integer>[] ids = new ArrayList[2];
        ArrayList<Integer> theaterIds = new ArrayList<>();

        Theater theater0 = theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25));
        Theater theater1 = theaterRepo.save(new Theater("Whatever", "Copenhagen", "Test", 11));

        theaterIds.add(theater0.getId());
        theaterIds.add(theater1.getId());

        ArrayList<Integer> hallIds = new ArrayList<Integer>();

        CinemaHall cinemaHall1 = cinemaHallRepo.save(new CinemaHall("A", 12, 16, theater0));
        CinemaHall cinemaHall2 = cinemaHallRepo.save(new CinemaHall("B", 8, 12, theater0));
        CinemaHall cinemaHall3 = cinemaHallRepo.save(new CinemaHall("C", 10, 14, theater0));
        CinemaHall cinemaHall4 = cinemaHallRepo.save(new CinemaHall("A", 7, 21, theater1));
        CinemaHall cinemaHall5 = cinemaHallRepo.save(new CinemaHall("B", 15, 4, theater1));
        CinemaHall cinemaHall6 = cinemaHallRepo.save(new CinemaHall("C", 9, 14, theater1));

        hallIds.add(cinemaHall1.getId());
        hallIds.add(cinemaHall2.getId());
        hallIds.add(cinemaHall3.getId());
        hallIds.add(cinemaHall4.getId());
        hallIds.add(cinemaHall5.getId());
        hallIds.add(cinemaHall6.getId());

        ids[0] = theaterIds;
        ids[1] = hallIds;
        return ids;
    }
    public static  ArrayList<Integer> setUpTheaters(TheaterRepo theaterRepo){
        theaterRepo.deleteAll();
        ArrayList<Integer> theaterIds = new ArrayList<>();
        theaterIds.add(theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25)).getId());
        theaterIds.add(theaterRepo.save(new Theater("Whatever", "Copenhagen", "Test", 11)).getId());
        return theaterIds;
    }
    public static void clearTheaters(TheaterRepo theaterRepo,UserRepository userRepository, RoleRepository roleRepository){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        theaterRepo.deleteAll();
    }
    public static void clearTheaterAndHall(TheaterRepo theaterRepo, CinemaHallRepo cinemaHallRepo){
        cinemaHallRepo.deleteAll();
        theaterRepo.deleteAll();
    }
    public static void clearGenre(GenreRepo genreRepo,UserRepository userRepository, RoleRepository roleRepository){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        genreRepo.deleteAll();
    }
    public static void clearCategory(CategoryRepo categoryRepo, UserRepository userRepository, RoleRepository roleRepository){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        categoryRepo.deleteAll();
    }
}