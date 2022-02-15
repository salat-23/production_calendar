package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "calendar_day")
public class Day {

    @EmbeddedId
    private DayId dayId;

    @ManyToOne
    @JoinColumn(name = "day_type_type")
    private DayType dayType;


}
