package com.example.demo.repository;

import com.example.demo.model.CalendarYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarYearRepository extends JpaRepository<CalendarYear, Short> {
}
