package com.future.cinemaxx.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Theater {

    @OneToMany(mappedBy = "theater")
    List<CinemaHall> cinemaHalls = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 40, nullable = false)
    private String name;
    @Column(length = 40, nullable = false)
    private String city;
    @Column(length = 40, nullable = false)
    private String street;
    @Column(nullable = false)
    private int streetNumber;

    public Theater(String name, String city, String street, int streetNumber) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }
}
