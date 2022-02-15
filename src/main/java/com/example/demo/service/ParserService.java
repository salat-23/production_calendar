package com.example.demo.service;


import com.example.demo.component.CSVParser;
import com.example.demo.component.CSVParserV1;
import com.example.demo.model.Day;
import com.example.demo.model.Month;
import com.example.demo.model.Year;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.MonthRepository;
import com.example.demo.repository.YearRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ParserService {


    private final CSVParser parser;
    private final DownloadResourceService downloadResourceService;
    private final YearRepository yearRepository;
    private final MonthRepository monthRepository;
    private final DayRepository dayRepository;

    public ParserService(DownloadResourceService downloadResourceService,
                         YearRepository yearRepository,
                         MonthRepository monthRepository,
                         DayRepository dayRepository,
                         CSVParser parser) {
        this.downloadResourceService = downloadResourceService;
        this.yearRepository = yearRepository;
        this.monthRepository = monthRepository;
        this.dayRepository = dayRepository;
        this.parser = parser;
    }

    public void parseAndSave() {

        try {
            String path = downloadResourceService.getLatestFilePath();
            parser.initialize(path, 1);
            while (parser.isAvailable()) {
                try {
                    String[] line = parser.getNextLine();
                    Arrays.stream(line).forEach(System.out::println);
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  private Set<Day> parseDays(List<String> formattedDays, Month month, Year year) {

    }*/


}
