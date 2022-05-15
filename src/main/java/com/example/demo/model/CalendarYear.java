package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "calendar_year")
public class CalendarYear {

    @Id
    private Integer year;
    private Integer totalWorkingDays;
    private Integer totalOffDays;
    private Float totalWorkingHours40Hours;
    private Float totalWorkingHours36Hours;
    private Float totalWorkingHours24Hours;

}
