package com.future.cinemaxx.configuration;

import com.future.cinemaxx.entities.*;
import com.future.cinemaxx.repositories.*;
import com.future.cinemaxx.security.entities.ERole;
import com.future.cinemaxx.security.entities.Role;
import com.future.cinemaxx.security.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@Component
@Profile("!test")
public class DataSetup implements CommandLineRunner {
    CategoryRepo categoryRepo;
    CinemaHallRepo cinemaHallRepo;
    GenreRepo genreRepo;
    MovieRepo movieRepo;
    ProjectionRepo projectionRepo;
    TheaterRepo theaterRepo;
    TicketRepo ticketRepo;
    RoleRepository roleRepository;

    public DataSetup(CategoryRepo categoryRepo,
                     CinemaHallRepo cinemaHallRepo,
                     GenreRepo genreRepo,
                     MovieRepo movieRepo,
                     ProjectionRepo projectionRepo,
                     TheaterRepo theaterRepo,
                     TicketRepo ticketRepo,
                     RoleRepository roleRepository){
        this.categoryRepo = categoryRepo;
        this.cinemaHallRepo = cinemaHallRepo;
        this.genreRepo = genreRepo;
        this.movieRepo = movieRepo;
        this.projectionRepo = projectionRepo;
        this.theaterRepo = theaterRepo;
        this.ticketRepo = ticketRepo;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);

        Role customerRole = new Role();
        customerRole.setName(ERole.ROLE_CUSTOMER);

        Role managerRole = new Role();
        managerRole.setName(ERole.ROLE_MANAGER);

        roleRepository.save(adminRole);
        roleRepository.save(customerRole);
        roleRepository.save(managerRole);

        Theater theater = theaterRepo.save(new Theater("Cinema city", "Copenhagen", "Husumtorv", 25));
        Theater theater1 = theaterRepo.save(new Theater("Whatever", "Copenhagen", "Test",11));

        CinemaHall cinemaHall1 = cinemaHallRepo.save(new CinemaHall("A", 6, 8, theater));
        CinemaHall cinemaHall2 = cinemaHallRepo.save(new CinemaHall("B", 6, 8, theater));
        CinemaHall cinemaHall3 = cinemaHallRepo.save(new CinemaHall("C", 6, 8, theater));
        CinemaHall cinemaHall4 = cinemaHallRepo.save(new CinemaHall("A", 6, 8, theater1));
        CinemaHall cinemaHall5 = cinemaHallRepo.save(new CinemaHall("B", 6, 8, theater1));
        CinemaHall cinemaHall6 = cinemaHallRepo.save(new CinemaHall("C", 6, 8, theater1));

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
        Movie movie5 = movieRepo.save(new Movie("Inception",Duration.ofMinutes(200) , genre3, category3));
        Movie movie6 = movieRepo.save(new Movie("Dark Knight",Duration.ofMinutes(220) , genre3, category3));
        Movie movie7 = movieRepo.save(new Movie("Deadpool",Duration.ofMinutes(163) , genre2, category4));

        Projection projection1 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall1, movie1));
        Projection projection2 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(7), 15.6f, cinemaHall1, movie2));
        Projection projection3 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall3, movie4));
        Projection projection4 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall2, movie3));
        Projection projection5 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(1).plusHours(2), 15.6f, cinemaHall4, movie2));
        Projection projection6 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(100).plusHours(2), 15.6f, cinemaHall4, movie2));
        Projection projection7 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(22).plusHours(12), 15.6f, cinemaHall4, movie2));
        Projection projection8 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(22), 15.6f, cinemaHall1, movie4));
        Projection projection9 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(11), 15.6f, cinemaHall2, movie4));
        Projection projection10 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(5), 15.6f, cinemaHall3, movie4));
        Projection projection11 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(5), 15.6f, cinemaHall3, movie4));
        Projection projection12 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(9), 15.6f, cinemaHall5, movie5));
        Projection projection13 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(12), 15.6f, cinemaHall5, movie5));
        Projection projection14 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(13), 15.6f, cinemaHall5, movie5));
        Projection projection15 = projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-12T12:30:00"), 15.6f, cinemaHall5, movie6));
        Projection projection16 = projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-14T11:35:00"), 15.6f, cinemaHall5, movie6));
        Projection projection17 = projectionRepo.save(new Projection(LocalDateTime.parse("2025-11-14T09:35:00"), 15.6f, cinemaHall4, movie7));
        Projection projection18 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall5, movie7));
        Projection projection19 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(10), 15.6f, cinemaHall6, movie7));
        Projection projection20 = projectionRepo.save(new Projection(LocalDateTime.now().plusDays(3), 15.6f, cinemaHall1, movie7));

        ticketRepo.saveAll(projection1.getTickets());
        ticketRepo.saveAll(projection2.getTickets());
        ticketRepo.saveAll(projection3.getTickets());
        ticketRepo.saveAll(projection4.getTickets());
        ticketRepo.saveAll(projection5.getTickets());
        ticketRepo.saveAll(projection6.getTickets());
        ticketRepo.saveAll(projection7.getTickets());
        ticketRepo.saveAll(projection8.getTickets());
        ticketRepo.saveAll(projection9.getTickets());
        ticketRepo.saveAll(projection10.getTickets());
        ticketRepo.saveAll(projection11.getTickets());
        ticketRepo.saveAll(projection12.getTickets());
        ticketRepo.saveAll(projection13.getTickets());
        ticketRepo.saveAll(projection14.getTickets());
        ticketRepo.saveAll(projection15.getTickets());
        ticketRepo.saveAll(projection16.getTickets());
        ticketRepo.saveAll(projection17.getTickets());
        ticketRepo.saveAll(projection18.getTickets());
        ticketRepo.saveAll(projection19.getTickets());
        ticketRepo.saveAll(projection20.getTickets());
    }
}
