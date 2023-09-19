package com.java.calenderpro;
public class Reminder {

    int beforeMinutes;

    Event event;

    Reminder(Event event,int beforeMinutes) {
        this.event = event;
        this.beforeMinutes = beforeMinutes;

    }

    @Override
    public String toString() {
        return "Reminder{" +
                ", beforeDays=" + beforeMinutes +
                '}';
    }
}
