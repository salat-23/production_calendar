package com.example.demo.controller;

import com.example.demo.dto.DateDTO;
import com.example.demo.model.CalendarDate;
import com.example.demo.repository.CalendarDateRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/date/span")
    public List<DateDTO> getSpan(@RequestParam("start_day") Integer startDay,
                                 @RequestParam("start_month") Integer startMonth,
                                 @RequestParam("start_year") Integer startYear,
                                 @RequestParam("end_day") Integer endDay,
                                 @RequestParam("end_month") Integer endMonth,
                                 @RequestParam("end_year") Integer endYear) {

        CalendarDate startDate = calendarDateRepository.getDate(startDay, startMonth, startYear);
        if (startDate == null) throw new EntityNotFoundException("Date was not found.");
        CalendarDate endDate = calendarDateRepository.getDate(endDay, endMonth, endYear);
        if (endDate == null) throw new EntityNotFoundException("Date was not found.");

        LocalDate startLocalDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endLocalDate = LocalDate.of(endYear, endMonth, endDay);

        if (!startLocalDate.isBefore(endLocalDate.plusDays(1)))
            throw new EntityNotFoundException("Start date must be before end date.");
        List<DateDTO> dates = new ArrayList<>();
        while (startLocalDate.isBefore(endLocalDate.plusDays(1))) {
            CalendarDate date = calendarDateRepository.getDate(
                    startLocalDate.getDayOfMonth(),
                    startLocalDate.getMonthValue(),
                    startLocalDate.getYear());
            dates.add(new DateDTO(date));
            startLocalDate = startLocalDate.plusDays(1);
        }

        return dates;
    }


}
