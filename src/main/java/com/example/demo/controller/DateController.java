package com.example.demo.controller;

import com.example.demo.dto.DateDTO;
import com.example.demo.model.CalendarDate;
import com.example.demo.repository.CalendarDateRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class DateController {

    private final CalendarDateRepository calendarDateRepository;

    public DateController(CalendarDateRepository calendarDateRepository) {
        this.calendarDateRepository = calendarDateRepository;
    }

    @GetMapping("/date/separate")
    public DateDTO getDate(@RequestParam("day") Integer day, @RequestParam("month") Integer month, @RequestParam("year") Integer year) {

        CalendarDate date = calendarDateRepository.getDate(day, month, year);
        if (date == null) throw new EntityNotFoundException("Date was not found.");

        return new DateDTO(LocalDate.of(date.getCalendarYear().getYear(), date.getMonth(), date.getDay()), date.getType());
    }

    @GetMapping("/date/string")
    public DateDTO getDate(@RequestParam("date") String paramDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate incomingDate = LocalDate.parse(paramDate, formatter);
        CalendarDate date = calendarDateRepository.getDate(
                incomingDate.getDayOfMonth(),
                incomingDate.getMonthValue(),
                incomingDate.getYear());
        if (date == null) throw new EntityNotFoundException("Date was not found.");

        return new DateDTO(LocalDate.of(date.getCalendarYear().getYear(), date.getMonth(), date.getDay()), date.getType());
    }


}
