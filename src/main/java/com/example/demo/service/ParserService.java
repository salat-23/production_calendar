package com.example.demo.service;


import com.example.demo.component.CSVParser;
import com.example.demo.component.CSVParserV1;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ParserService {


    private CSVParser parser;
    private DownloadResourceService downloadResourceService;

    public ParserService(DownloadResourceService downloadResourceService) {
        this.downloadResourceService = downloadResourceService;
        parser = new CSVParserV1();
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


}
