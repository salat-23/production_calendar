package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "day_type")
public class DayType {

    @Id
    @Enumerated(EnumType.STRING)
    private EDayType type;

}
