package com.java.calenderpro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {

    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    String name;
    String description;
    String location;
    String tag;
    LocalDateTime dateTime;

    public Event(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String name,
            String description, String location, String tag) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.location = location;
        this.tag = tag;
        this.dateTime = LocalDateTime.of(startDate, startTime);
    }

    Event() {
    }

}
