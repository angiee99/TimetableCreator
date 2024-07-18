package org.example.timetable.service;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;

import java.util.List;

public interface MutationService {
    List<Individual> mutate(List<Individual> population, List<Activity> activities);
}
