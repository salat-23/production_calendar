package com.example.demo.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class DayId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "calendar_month_id")
    private Month month;
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DayId dayId = (DayId) o;
        return month != null && Objects.equals(month, dayId.month)
                && number != null && Objects.equals(number, dayId.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, number);
    }
}
