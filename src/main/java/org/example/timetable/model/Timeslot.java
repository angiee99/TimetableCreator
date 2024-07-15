package org.example.timetable.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Timeslot {
    DayOfWeek day;
    LocalTime start;
    LocalTime end;

    public long getDuration(){
        return MINUTES.between(start, end);
    }
    // TODO: compare by day (Serializable?)

    public Timeslot(DayOfWeek day, LocalTime start, LocalTime end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public Timeslot() {
    }


    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
