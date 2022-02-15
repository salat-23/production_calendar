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
@Table(name = "day_type")
public class DayType {

    @Id
    @Enumerated(EnumType.STRING)
    private EDayType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DayType dayType = (DayType) o;
        return type != null && Objects.equals(type, dayType.type);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
