package org.example.timetable.service;

import org.example.timetable.model.Activity;

import java.util.List;

public interface GeneticAlgStarterService {
    List<Activity> createSchedule(List<Activity> activities);
}
