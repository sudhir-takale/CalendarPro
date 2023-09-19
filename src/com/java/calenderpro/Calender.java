package com.java.calenderpro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Calender {
    public static List<Event> eventList = new ArrayList<>();
    public static List<Reminder> reminderList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private Thread reminderThread;

    Calender() {
        reminderThread = new Thread(new ReminderSetter(reminderList, this));
        reminderThread.start();
    }

    public static void displayReminder(Event event, int minutesBefore) {
        LocalDateTime reminderTime = event.dateTime.minusMinutes(minutesBefore);
        LocalDateTime currentTime = LocalDateTime.now();

        if (reminderTime.isAfter(currentTime)) {

            Thread reminderThread = new Thread(() -> {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            reminderThread.start();
        } else {
            System.out.println("--- You have A Event ---");
            System.out.println("Event " + event.name);
            System.out.println("Event Date: " + event.startDate);
            System.out.println("Event Time: " + event.startTime);
            System.out.println("Location: " + event.location);
            System.out.println("Description: " + event.description);
        }
    }

    public static boolean checkDuplicate(LocalDate startDate, LocalTime startTime, LocalDate endDate) {
        for (Event event : eventList) {
            if (event.startDate.equals(startDate) && event.startTime.equals(startTime)
                    && event.endDate.equals(endDate)) {
                return true;
            }
        }
        return false;
    }

    public void addEvent() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.print("Enter start date (dd-MM-yyyy): ");
        LocalDate startDate = readValidDate(dateFormatter);
        System.out.print("Enter start time (HH:mm): ");
        LocalTime startTime = readValidTime(timeFormatter);

        System.out.print("Enter end date (dd-MM-yyyy): ");
        LocalDate endDate = readValidDate(dateFormatter);

        if (checkDuplicate(startDate, startTime, endDate)) {
            System.out.println("Event already exist ... Choose another Time Slot");
        } else {
            System.out.print("Enter end time (HH:mm): ");
            LocalTime endTime = readValidTime(timeFormatter);

            scanner.nextLine();
            System.out.print("Enter event Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter event Description: ");
            String description = scanner.nextLine();

            System.out.print("Enter event Location: ");
            String location = scanner.nextLine();

            System.out.print("Enter event Tag (Personal/Work/Health/Other): ");
            String tag = scanner.nextLine();

            Event event = new Event(startDate, endDate, startTime, endTime, name, description, location, tag);

            if (checkEvent(event)) {
                eventList.add(event);
                event.dateTime = LocalDateTime.of(startDate, startTime);
                System.out.println("Event added Successfully...");
                System.out.println("Do you want to add Reminder (Y/N)");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println("Enter how much minutes before you want the reminder:");
                    int minutes = scanner.nextInt();
                    setReminder(event, minutes);
                }
                else if(choice.equalsIgnoreCase("N"));
            }
        }

    }

    LocalTime readValidTime(DateTimeFormatter formatter) {
        while (true) {
            try {
                String input = scanner.next();
                LocalTime time = LocalTime.parse(input, formatter);
                return time;
            } catch (DateTimeParseException e) {
                System.out.println("Wrong Format HH:mm");
            }
        }
    }

    LocalDate readValidDate(DateTimeFormatter formatter) {
        while (true) {
            try {
                String input = scanner.next();
                LocalDate date = LocalDate.parse(input, formatter);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println(" Wrong format dd-MM-yyyy.");
            }
        }
    }

    // Check if Event already Exist
    boolean checkEvent(Event event) {
        for (Event event1 : eventList) {
            if (event1.startDate != null && event1.startTime != null &&
                    event.startDate != null && event.startTime != null) {
                if (event1.startDate.equals(event.startDate) && event1.startTime.equals(event.startTime)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setReminder(Event event, int days) {

        Reminder reminder = new Reminder(event, days);
        reminderList.add(reminder);
        System.out.println("Reminder added Successfully");

    }

    void categorizeEvent(String tag) {
        System.out.println(tag.toUpperCase());
        System.out.println("..................");
        for (Event event : eventList) {

            if (event.tag.equalsIgnoreCase(tag)) {
                System.out.println("Event Name: " + event.name);
                System.out.println("Start Date: " + event.startDate);
                System.out.println("Start Time: " + event.startTime);
                System.out.println("Location: " + event.location);
                System.out.println("Description: " + event.description);
                System.out.println("--------------------------------");
            }
        }
    }

    // to Display all the events
    void displayAllEvents() {
        if (eventList.size() == 0) {
            System.out.println("No Events to show.......");
        } else {
            for (Event event : eventList) {
                System.out.println("Event Name: " + event.name);
                System.out.println("Start Date: " + event.startDate);
                System.out.println("Start Time: " + event.startTime);
                System.out.println("Location: " + event.location);
                System.out.println("Description: " + event.description);
                System.out.println("Tag: " + event.tag);
                System.out.println("--------------------------------");

            }
        }
    }

    public void displayOverview(LocalDate date, String choice) {
        if (date == null || choice.isEmpty()) {
            System.out.println("Invalid date or choice.");
            return;
        }
        if (reminderList.size() == 0) {
            System.out.println("No Events to show....");
        }
        if (choice.equalsIgnoreCase("day")) {
            for (Event event : eventList) {
                if (event.startDate.equals(date)) {
                    System.out.println("Event Name: " + event.name);
                    System.out.println("Start Date: " + event.startDate);
                    System.out.println("Start Time: " + event.startTime);
                    System.out.println("Location: " + event.location);
                    System.out.println("Description: " + event.description);
                    System.out.println();
                }
            }
        } else if (choice.equalsIgnoreCase("week")) {
            LocalDate endDate = date.plusDays(6); // Calculate end date of the week

            for (Event event : eventList) {
                if (event.startDate.isEqual(date)
                        || (event.startDate.isAfter(date) && event.startDate.isBefore(endDate))) {
                    System.out.println("Event Name: " + event.name);
                    System.out.println("Start Date: " + event.startDate);
                    System.out.println("Start Time: " + event.startTime);
                    System.out.println("Location: " + event.location);
                    System.out.println("Description: " + event.description);
                    System.out.println();
                }
            }
        } else if (choice.equalsIgnoreCase("month")) {
            LocalDate startDate = date.withDayOfMonth(1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            for (Event event : eventList) {
                if (event.startDate.isEqual(date)
                        || (event.startDate.isAfter(startDate) && event.startDate.isBefore(endDate))) {
                    System.out.println("Event Name: " + event.name);
                    System.out.println("Start Date: " + event.startDate);
                    System.out.println("Start Time: " + event.startTime);
                    System.out.println("Location: " + event.location);
                    System.out.println("Description: " + event.description);
                    System.out.println();
                }
            }
        }
    }

}

class ReminderSetter implements Runnable {
    List<Reminder> reminders = new ArrayList<>();
    private Set<Reminder> uniqueReminders = new HashSet<>();
    private Calender calender;

    ReminderSetter(List<Reminder> reminders, Calender calender) {
        this.reminders = reminders;
        this.calender = calender;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            LocalDateTime currentTime = LocalDateTime.now();

            for (Reminder reminder : reminders) {
                LocalDateTime reminderTime = reminder.event.dateTime.minusMinutes(reminder.beforeMinutes);

                if (currentTime.isAfter(reminderTime)) {
                    if (!uniqueReminders.contains(reminder)) {
                        Calender.displayReminder(reminder.event, reminder.beforeMinutes);
                        uniqueReminders.add(reminder);
                    }
                }
            }

            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
