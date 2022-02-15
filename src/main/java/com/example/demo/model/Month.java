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
@Table(name = "calendar_month")
public class Month {

    @EmbeddedId
    private MonthId monthId;

    @Column(name = "month_number")
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Month month = (Month) o;
        return monthId != null && Objects.equals(monthId, month.monthId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
