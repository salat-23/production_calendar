package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(EntityNotFoundException.class)
    public String notFound() {
        return "Дата вне диапазона";
    }

}
