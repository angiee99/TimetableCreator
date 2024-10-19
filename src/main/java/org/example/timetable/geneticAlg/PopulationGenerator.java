package org.example.timetable.geneticAlg;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Generation;

import java.util.List;

public interface PopulationGenerator {
    Generation generate(List<Activity> activities, int populationSize);
}
