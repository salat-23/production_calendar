package com.example.demo;

import com.example.demo.service.ParserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	ParserService service;

	@Test
	void contextLoads() {

		service.parseAndSave();

	}

}
