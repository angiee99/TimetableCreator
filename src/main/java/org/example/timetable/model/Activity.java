package org.example.timetable.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Activity {
    ActivityType activityType;
    DayOfWeek day;
    LocalTime start;
    LocalTime end;
    String room;
    Boolean isAvailable;
    // TODO: compare by day
    public long getDuration(){
        return MINUTES.between(start, end);
    }

    public Activity(ActivityType activityType, DayOfWeek day, LocalTime start, LocalTime end, String room, Boolean isAvailable) {
        this.activityType = activityType;
        this.day = day;
        this.start = start;
        this.end = end;
        this.room = room;
        this.isAvailable = isAvailable;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Activity() {
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
