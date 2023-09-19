package com.java.calenderpro;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReminderThread implements Runnable {

    private List<Reminder> reminderList;

    public ReminderThread(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public void run() {
        while (true) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            List<Reminder> remindersToRemove = new ArrayList<>();

            for (Reminder reminder : reminderList) {
                LocalDate eventStartDate = reminder.event.startDate.minusDays(reminder.beforeMinutes);
                LocalTime eventStartTime = reminder.event.startTime;

                if (currentDate.equals(eventStartDate) && currentTime.equals(eventStartTime)) {
                    System.out.println("REMINDER......");
                    System.out.println("Event Name: " + reminder.event.name);
                    System.out.println("Event Date: " + reminder.event.startDate);
                    System.out.println("Event Time: " + reminder.event.startTime);
                    System.out.println("Reminder Description: " + reminder.event.description);
                    System.out.println();

                    remindersToRemove.add(reminder);
                }
            }

            reminderList.removeAll(remindersToRemove);

            try {
                // Sleep for a while before checking again
                Thread.sleep(1000); // Adjust as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
