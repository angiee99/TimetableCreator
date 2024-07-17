package org.example.timetable.service;

import org.example.timetable.model.Individual;

import java.util.List;

public interface CrossoverService {
    List<Individual> crossover(List<Individual> parentPopulation, int populationSize);
}
