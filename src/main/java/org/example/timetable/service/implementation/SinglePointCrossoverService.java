package org.example.timetable.service.implementation;

import org.example.timetable.model.Individual;
import org.example.timetable.service.CrossoverService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SinglePointCrossoverService implements CrossoverService {
    @Override
    public List<Individual> crossover(List<Individual> parentPopulation,  int populationSize) {
        return null;
    }
}
