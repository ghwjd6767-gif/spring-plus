package org.example.expert.domain.todo.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record WeatherCondition(
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate startDate,

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate endDate,

        String weather
) {

}
