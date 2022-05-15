package com.example.demo.repository;

import com.example.demo.model.CalendarDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, Long> {

    @Query(nativeQuery = true, value = "select * from calendar_date inner join calendar_year cy on cy.year = calendar_date.calendar_year_year where day = :day and month = :month and year = :year")
    CalendarDate getDate(@Param("day") int day, @Param("month") int month, @Param("year") int year);

}
