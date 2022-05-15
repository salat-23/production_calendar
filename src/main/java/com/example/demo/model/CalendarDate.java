package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "calendar_date")
public class CalendarDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CalendarYear calendarYear;
    @Enumerated(EnumType.STRING)
    private EDayType type;
    private Integer month;
    private Integer day;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 12 * hash + month;
        hash = 8 * hash + day;
        hash = hash * type.ordinal() + 15;
        return hash;
    }

    public boolean equals(CalendarDate otherDate) {
        return (this.day.equals(otherDate.getDay())) &&
                (this.month.equals(otherDate.getMonth())) &&
                (this.calendarYear.getYear().equals(otherDate.getCalendarYear().getYear()));
    }
}
