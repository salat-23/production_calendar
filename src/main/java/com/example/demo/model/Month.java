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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_year")
    private Year year;

    @Column(name = "month_name")
    private String name;

    @Column(name = "month_number")
    private Integer number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Month month = (Month) o;
        return id != null && Objects.equals(id, month.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
