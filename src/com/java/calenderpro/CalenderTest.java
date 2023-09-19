package com.java.calenderpro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

public class CalenderTest {

    @BeforeEach
    public void setUp() {
        // Clear eventList and reminderList before each test
        Calender.eventList.clear();
        Calender.reminderList.clear();
    }

    @Test
    void testAddEvent() {
        String input = "01-01-2023\n12:00\n02-01-2023\n13:00\nEvent Name\nEvent Description\nEvent Location\nPersonal\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Calender calender = new Calender();

        calender.addEvent();

        assertEquals(1, Calender.eventList.size());
        assertEquals(1, Calender.reminderList.size());
    }

    @Test
    void testCheckDuplicate() {
        LocalDate startDate = LocalDate.parse("01-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime startTime = LocalTime.parse("12:00", DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate endDate = LocalDate.parse("02-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Event event = new Event(startDate, endDate, startTime, startTime, "Event Name", "Event Description", "Event Location", "Personal");

        Calender.eventList.add(event);

        assertTrue(Calender.checkDuplicate(startDate, startTime, endDate));
        assertFalse(Calender.checkDuplicate(LocalDate.parse("03-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")), startTime, endDate));
    }
}
