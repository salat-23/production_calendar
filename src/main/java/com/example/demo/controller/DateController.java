package com.example.demo.controller;

import com.example.demo.dto.DateDTO;
import com.example.demo.model.CalendarDate;
import com.example.demo.model.CalendarYear;
import com.example.demo.model.EDayType;
import com.example.demo.repository.CalendarDateRepository;
import com.example.demo.repository.CalendarYearRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class DateController {

    private final CalendarDateRepository calendarDateRepository;
    private final CalendarYearRepository calendarYearRepository;

    public DateController(CalendarDateRepository calendarDateRepository,
                          CalendarYearRepository calendarYearRepository) {
        this.calendarDateRepository = calendarDateRepository;
        this.calendarYearRepository = calendarYearRepository;
    }

    @GetMapping("/date/separate")
    public DateDTO getDate(@RequestParam("day") Integer day, @RequestParam("month") Integer month, @RequestParam("year") Integer year) {

        CalendarDate date = calendarDateRepository.getDate(day, month, year);
        if (date == null) throw new EntityNotFoundException("Date was not found.");

        return new DateDTO(LocalDate.of(date.getCalendarYear().getYear(), date.getMonth(), date.getDay()), date.getType());
    }

    @GetMapping("/date/change")
    public DateDTO changeDate(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("date") String date,
            @RequestParam("type") String type) {

        if (!login.equals("admin") || !password.equals("admin")) {
            throw new RuntimeException("Wrong credentials!");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate incomingDate = LocalDate.parse(date, formatter);

        CalendarDate calendarDate = calendarDateRepository.getDate(
                incomingDate.getDayOfMonth(),
                incomingDate.getMonthValue(),
                incomingDate.getYear());
        if (calendarDate == null) {
            Optional<CalendarYear> calendarYearOptional = calendarYearRepository.findById(incomingDate.getYear());
            CalendarYear calendarYear;
            if (calendarYearOptional.isEmpty()) {
                calendarYear = new CalendarYear();
                calendarYear.setYear(incomingDate.getYear());
            } else {
                calendarYear = calendarYearOptional.get();
            }
            CalendarDate newCalendarDate = new CalendarDate();
            newCalendarDate.setCalendarYear(calendarYear);
            newCalendarDate.setDay(incomingDate.getDayOfMonth());
            newCalendarDate.setMonth(incomingDate.getMonthValue());
            try {
                EDayType dayType = EDayType.valueOf(type);
                newCalendarDate.setType(dayType);
            } catch (Exception e) {
                throw new RuntimeException("Illegal date type");
            }
            calendarYearRepository.save(calendarYear);
            calendarDateRepository.save(newCalendarDate);
            return new DateDTO(newCalendarDate);
        }
        try {
            EDayType dayType = EDayType.valueOf(type);
            calendarDate.setType(dayType);
        } catch (Exception e) {
            throw new RuntimeException("Illegal date type");
        }
        Optional<CalendarYear> calendarYearOptional = calendarYearRepository.findById(incomingDate.getYear());
        CalendarYear calendarYear;
        if (calendarYearOptional.isEmpty()) {
            calendarYear = new CalendarYear();
            calendarYear.setYear(incomingDate.getYear());
        } else {
            calendarYear = calendarYearOptional.get();
        }
        calendarDate.setCalendarYear(calendarYear);
        calendarDate.setDay(incomingDate.getDayOfMonth());
        calendarDate.setMonth(incomingDate.getMonthValue());
        calendarYearRepository.save(calendarYear);
        calendarDateRepository.save(calendarDate);
        return new DateDTO(calendarDate);
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
