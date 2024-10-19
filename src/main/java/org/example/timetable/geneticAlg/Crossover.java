package org.example.timetable.geneticAlg;

import org.example.timetable.model.Individual;

import java.util.List;

public interface Crossover {
    List<Individual> doCrossover(List<Individual> parentPopulation, int populationSize);
}
