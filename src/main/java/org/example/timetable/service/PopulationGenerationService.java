package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Generation;

import java.util.List;

public interface PopulationGenerationService {
    Generation generate(List<Activity> activities, int populationSize);
}
