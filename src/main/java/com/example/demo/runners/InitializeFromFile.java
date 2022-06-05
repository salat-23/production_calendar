package com.example.demo.runners;

import com.example.demo.service.ParserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeFromFile implements CommandLineRunner {

    private final ParserService parserService;

    public InitializeFromFile(ParserService parserService) {
        this.parserService = parserService;
    }

    @Override
    public void run(String... args) throws Exception {
        parserService.parseAndSave();
    }
}
