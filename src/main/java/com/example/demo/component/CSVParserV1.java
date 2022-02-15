package com.example.demo.component;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class CSVParserV1 implements CSVParser {

    private CSVReader reader;

    @Override
    public String[] getNextLine() throws CsvValidationException, IOException {
        return reader.readNext();
    }

    @Override
    public List<String[]> getAll() throws IOException, CsvException {
        return reader.readAll();
    }

    @Override
    public Boolean isAvailable() throws IOException {
        return (reader.peek() != null);
    }

    @Override
    public void initialize(String filepath, Integer skippedLines) throws IOException {
        reader = new CSVReader(new FileReader(filepath));
        reader.skip(skippedLines);
    }
}
