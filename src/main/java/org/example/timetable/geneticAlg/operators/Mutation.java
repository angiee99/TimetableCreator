package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;

import java.util.List;

public interface Mutation {
    List<Individual> mutate(List<Individual> population, List<Activity> activities);
}
