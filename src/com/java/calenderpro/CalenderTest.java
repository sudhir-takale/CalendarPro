package com.java.calenderpro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalenderTest {

    @BeforeEach
    public void setUp() {

        Calender.eventList.clear();
        Calender.reminderList.clear();
    }


    @Test
    void testCheckDuplicate() {
        LocalDate startDate = LocalDate.parse("01-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime startTime = LocalTime.parse("12:00", DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate endDate = LocalDate.parse("02-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Event event = new Event(startDate, endDate, startTime, startTime, "Event Name", "Event Description",
                "Event Location", "Personal");

        Calender.eventList.add(event);

        assertTrue(Calender.checkDuplicate(startDate, startTime, endDate));
        assertFalse(Calender.checkDuplicate(LocalDate.parse("03-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                startTime, endDate));

    }


    @Test
    void testDisplayReminder() {
        LocalDate startDate = LocalDate.parse("01-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime startTime = LocalTime.parse("12:00", DateTimeFormatter.ofPattern("HH:mm"));
        Event event = new Event(startDate, startDate, startTime, startTime, "Event Name", "Event Description",
                "Event Location", "Personal");

        Calender.eventList.add(event);

        String input = "01-01-2023\n12:00\n02-01-2023\n13:00\nEvent Name\nEvent Description\nEvent Location\nPersonal\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Calender calender = new Calender();
        calender.addEvent();


    }


    @Test
    void showALlEvent() {
        Event event = new Event("12-12-2001", "12-12-1212", "12:12", "12:34", "Event Name", "Event Description",
                "Event Location", "Personal");


        Calender.eventList.add(event);





    }


}
