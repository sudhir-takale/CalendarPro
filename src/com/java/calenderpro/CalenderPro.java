package com.java.calenderpro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CalenderPro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calender calender = new Calender();
        // calender.startReminderThread();
        System.out.println("Welcome to CalenderPro");

        while (true) {
            System.out.println("1 . Add new Event");
            System.out.println("2 . view Events (day/week/Month)");
            System.out.println("3 . Categorize Events");
            System.out.println("4 . view All Events");
            System.out.println("5 . Exit");
            System.out.println("Enter your Choice");

            switch (scanner.nextInt()) {

                case 1:
                    calender.addEvent();
                    break;

                case 2:

                    scanner.nextLine();
                    System.out.println("How you want to see Events(day/week/Month)");
                    System.out.print("Enter a date (yyyy-MM-dd): ");
                    String startingDate = scanner.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        LocalDate date = LocalDate.parse(startingDate, dateFormatter);
                        System.out.println("Enter your Choice(day/week/month)");
                        String choice = scanner.next();
                        System.out.println("-------------------------------");
                        calender.displayOverview(date, choice);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    break;

                case 3: {

                    System.out.println("Enter a Tag to Categorize Events (Personal/Work/Health/Other)");
                    calender.categorizeEvent(scanner.next());
                    break;
                }
                case 4:
                    System.out.println("Showing All Events");
                    System.out.println("-----------------------");
                    calender.displayAllEvents();
                    break;
                case 5:
                    System.exit(0);

                default:
                    throw new IllegalStateException("Unexpected value: " + scanner.nextInt());
            }


        }

    }
}
