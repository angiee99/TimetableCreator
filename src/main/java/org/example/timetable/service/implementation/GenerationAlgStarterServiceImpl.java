package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;
import org.example.timetable.service.GenerationAlgStarterService;
import org.example.timetable.service.PopulationGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerationAlgStarterServiceImpl implements GenerationAlgStarterService {
    private PopulationGenerationService populationGenerator;
    private final int GENERATION_COUNT = 100;
    private final int POPULATION_SIZE = 50;
    @Autowired
    public void setPopulationGenerator(PopulationGenerationService populationGenerator) {
        this.populationGenerator = populationGenerator;
    }

    @Override
    public List<Activity> createSchedule(List<Activity> activities) {
        List<Individual> population = populationGenerator.generate(activities, POPULATION_SIZE);
        for (int i = 0; i < GENERATION_COUNT; i++) {
            //TODO:
            // selection (need of a fitness function for that),
            // crossover
            // mutation

            // break if the fitness target was met
        }
        return null;
    }
}
