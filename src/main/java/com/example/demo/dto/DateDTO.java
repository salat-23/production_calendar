package com.example.demo.dto;

import com.example.demo.model.EDayType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DateDTO {

    public DateDTO() {}

    public DateDTO(LocalDate date, EDayType type) {
        this.date = date;
        switch (type) {
            case DAY_OFF:
                this.type = "Выходной";
                break;
            case DAY_OFF_ADDED:
                this.type = "Добавленный выходной";
                break;
            case WORKING:
                this.type = "Рабочий";
                break;
            case WORKING_HOLIDAY:
                this.type = "Праздничный";
                break;
        }
    }

    private LocalDate date;
    private String type;

}
