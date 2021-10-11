package com.future.cinemaxx.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity @Getter @Setter
@Table(name = "projections")
@DiscriminatorValue("1")
public class Projection extends Event{

    @ManyToOne
    private Movie movie;
}
