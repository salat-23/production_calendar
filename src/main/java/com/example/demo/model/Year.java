package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "calendar")
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long year;

    @Column(name = "total_working_days")
    private Integer totalWorkingDays;
    @Column(name = "total_weekends")
    private Integer totalWeekends;
    @Column(name = "total_working_days_40_hours_week")
    private Integer totalWorking40hoursDays;
    @Column(name = "total_working_days_36_hours_week")
    private Integer totalWorking36hoursDays;
    @Column(name = "total_working_days_24_hours_week")
    private Integer totalWorking24hoursDays;

}
