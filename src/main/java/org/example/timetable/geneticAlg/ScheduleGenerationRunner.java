package org.example.timetable.geneticAlg;

import org.example.timetable.model.Activity;

import java.util.List;

public interface ScheduleGenerationRunner {
    List<Activity> createSchedule(List<Activity> activities);
}
