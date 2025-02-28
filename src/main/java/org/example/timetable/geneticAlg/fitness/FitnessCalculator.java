package org.example.timetable.geneticAlg.fitness;

import org.example.timetable.model.Individual;

public interface FitnessCalculator {
    int fitness(Individual individual);
}
