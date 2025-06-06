package org.example.timetable.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Activity {
    ActivityType activityType;
    Timeslot timeslot;
    String room;
    Boolean isAvailable;
    public Activity(ActivityType activityType, Timeslot timeslot, String room, Boolean isAvailable) {
        this.activityType = activityType;
        this.timeslot = timeslot;
        this.room = room;
        this.isAvailable = isAvailable;
    }
    public Activity() {
    }
    @JsonIgnore
    public long getDuration(){
        return timeslot.getDuration();
    }
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
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
