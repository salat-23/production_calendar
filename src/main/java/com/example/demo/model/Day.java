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
@Table(name = "calendar_day")
public class Day {

    @EmbeddedId
    private DayId dayId;

    @ManyToOne
    @JoinColumn(name = "day_type_type")
    private DayType dayType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Day day = (Day) o;
        return dayId != null && Objects.equals(dayId, day.dayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayId);
    }
}
