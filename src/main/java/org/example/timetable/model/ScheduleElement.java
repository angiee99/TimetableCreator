package org.example.timetable.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScheduleElement {
    String subject;
    int type; // 0 - lecture, 1 - practice -> could be enum
    DayOfWeek day;
    LocalTime start;
    LocalTime end;
    String room;
    Boolean isAvailable;
    // TODO: compare by day
    public long getDuration(){
        return MINUTES.between(start, end);
    }
    public ScheduleElement(String subject, int type, DayOfWeek day, LocalTime start, LocalTime end, String room, Boolean isAvailable) {
        this.subject = subject;
        this.type = type;
        this.day = day;
        this.start = start;
        this.end = end;
        this.room = room;
        this.isAvailable = isAvailable;
    }

    public ScheduleElement() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
