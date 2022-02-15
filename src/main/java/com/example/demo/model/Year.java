package com.example.demo.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Year year1 = (Year) o;
        return year != null && Objects.equals(year, year1.year);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
