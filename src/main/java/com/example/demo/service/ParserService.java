package com.example.demo.service;


import com.example.demo.component.CSVParser;
import com.example.demo.model.CalendarDate;
import com.example.demo.model.CalendarYear;
import com.example.demo.model.EDayType;
import com.example.demo.repository.CalendarDateRepository;
import com.example.demo.repository.CalendarYearRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ParserService {
    private final CSVParser parser;
    private final DownloadResourceService downloadResourceService;
    private final CalendarDateRepository calendarDateRepository;
    private final CalendarYearRepository calendarYearRepository;

    public ParserService(DownloadResourceService downloadResourceService,
                         CSVParser parser,
                         CalendarDateRepository calendarDateRepository,
                         CalendarYearRepository calendarYearRepository) {
        this.downloadResourceService = downloadResourceService;
        this.parser = parser;
        this.calendarDateRepository = calendarDateRepository;
        this.calendarYearRepository = calendarYearRepository;
    }

    public void parseAndSave() {
        try {
            //String path = downloadResourceService.getLatestFilePath();
            parser.initialize("documents/somefile.csv", 1);
            while (parser.isAvailable()) {
                try {
                    String[] line = parser.getNextLine();

                    //0 is the year index in csv
                    Integer year = Integer.parseInt(line[0]);
                    Integer totalWorkingDays = Integer.parseInt(line[13]);
                    Integer totalOffDays = Integer.parseInt(line[14]);
                    Float totalWorkingHours40Hours = Float.parseFloat(line[15]);
                    Float totalWorkingHours36Hours = Float.parseFloat(line[16]);
                    Float totalWorkingHours24Hours = Float.parseFloat(line[17]);

                    CalendarYear calendarYear = new CalendarYear();
                    calendarYear.setYear(year);
                    calendarYear.setTotalWorkingDays(totalWorkingDays);
                    calendarYear.setTotalOffDays(totalOffDays);
                    calendarYear.setTotalWorkingHours24Hours(totalWorkingHours24Hours);
                    calendarYear.setTotalWorkingHours36Hours(totalWorkingHours36Hours);
                    calendarYear.setTotalWorkingHours40Hours(totalWorkingHours40Hours);

                    calendarYearRepository.save(calendarYear);

                    List<CalendarDate> parsedDates = new ArrayList<>();

                    //All months each containing sequence of days separated by comma e.g.: (1,2,3,4*,5,8+,9,10)
                    for (int i = 1; i <= 12; i++) {

                        final String movedDayRegex = "[\\d]+\\*";
                        final String addedDayRegex = "[\\d]+\\+";

                        String[] stringDays = line[i].split(",");

                        Integer monthNumber = i;
                        Stream.of(stringDays).forEach(stringDay -> {

                            EDayType type;
                            if (stringDay.matches(addedDayRegex))
                                type = EDayType.DAY_OFF_ADDED;
                            else if (stringDay.matches(movedDayRegex))
                                type = EDayType.WORKING_HOLIDAY;
                            else
                                type = EDayType.DAY_OFF;

                            Integer dayNumber = Integer.parseInt(stringDay.replaceAll("[*+]+", ""));

                            CalendarDate calendarDate = new CalendarDate();
                            calendarDate.setCalendarYear(calendarYear);
                            calendarDate.setType(type);
                            calendarDate.setMonth(monthNumber);
                            calendarDate.setDay(dayNumber);

                            parsedDates.add(calendarDate);
                        });
                    }

                    LocalDate endDate = LocalDate.of(year, 12, 31).plusDays(1);
                    //Iterate through entire year and parse all possible dates
                    outerLoop:
                    for (LocalDate date = LocalDate.of(year, 1, 1);
                         date.isBefore(endDate);
                         date = date.plusDays(1)) {

                        CalendarDate currentDate = new CalendarDate();
                        currentDate.setCalendarYear(calendarYear);
                        currentDate.setMonth(date.getMonth().getValue());
                        currentDate.setDay(date.getDayOfMonth());

                        for (CalendarDate listDate : parsedDates) {
                            if (listDate.equals(currentDate)) {
                                calendarDateRepository.save(listDate);
                                continue outerLoop;
                            }
                        }
                        currentDate.setType(EDayType.WORKING);
                        calendarDateRepository.save(currentDate);
                    }

                } catch (IOException | CsvValidationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}