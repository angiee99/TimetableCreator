package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface OutputService {
    Object formatOutput(List<Activity> schedule);
}
