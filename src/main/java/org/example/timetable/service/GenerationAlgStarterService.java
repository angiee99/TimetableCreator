package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface GenerationAlgStarterService {
    List<Activity> createSchedule(List<Activity> activities);
}
