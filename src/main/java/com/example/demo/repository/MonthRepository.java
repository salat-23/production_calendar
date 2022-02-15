package com.example.demo.repository;

import com.example.demo.model.Month;
import com.example.demo.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthRepository extends JpaRepository<Month, Long> {



}
