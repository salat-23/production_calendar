package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data

@Embeddable
public class MonthId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "calendar_year")
    private Year year;

    @Column(name = "month_name")
    private String name;

}
