package org.example.timetable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Timeslot {
    DayOfWeek day;
    LocalTime start;
    LocalTime end;
    public Timeslot(DayOfWeek day, LocalTime start, LocalTime end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }
    @JsonIgnore
    public long getDuration(){
        return MINUTES.between(start, end);
    }
    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
