package com.example.demo.component;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public interface CSVParser {

    String[] getNextLine() throws CsvValidationException, IOException;

    List<String[]> getAll() throws IOException, CsvException;

    Boolean isAvailable() throws IOException;

    void initialize(String filepath, Integer skippedLines) throws IOException;

}
