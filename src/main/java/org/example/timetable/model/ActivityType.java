package org.example.timetable.model;

import java.util.Objects;

public class ActivityType {
    String subject;
    int type; // 0 - lecture, 1 - practice

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityType that)) return false;
        return type == that.type && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, type);
    }

    public ActivityType(String subject, int type) {
        this.subject = subject;
        this.type = type;
    }

    public ActivityType() {
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
}
