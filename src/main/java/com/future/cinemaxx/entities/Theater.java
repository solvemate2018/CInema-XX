package com.future.cinemaxx.entities;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor
public class Theater {

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

    @OneToMany(mappedBy = "theater")
    List<CinemaHall> cinemaHalls = new ArrayList<>();

    public Theater(String name, String city, String street, int streetNumber){
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }
}
