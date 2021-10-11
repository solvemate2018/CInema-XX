package com.future.cinemaxx.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter @AllArgsConstructor
@NoArgsConstructor @Table(name = "activities")
@DiscriminatorValue("2")
public class Activity extends Event{

    @Basic
    @Column()
    private String name;

    @Basic
    @Column()
    private String description;
}
