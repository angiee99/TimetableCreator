package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;

import java.util.List;

public interface PopulationGenerationService {
    List<Individual> generate(List<Activity> activities);
}
