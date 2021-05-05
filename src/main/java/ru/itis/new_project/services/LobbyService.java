package ru.itis.new_project.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LobbyService {
    public static LocalDate getDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
