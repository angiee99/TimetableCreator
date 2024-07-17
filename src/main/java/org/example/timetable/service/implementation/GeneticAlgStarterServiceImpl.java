package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;
import org.example.timetable.service.GeneticAlgStarterService;
import org.example.timetable.service.PopulationGenerationService;
import org.example.timetable.service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneticAlgStarterServiceImpl implements GeneticAlgStarterService {
    private PopulationGenerationService populationGenerator;
    private SelectionService selectionService;
    private final int GENERATION_COUNT = 100;
    private final int POPULATION_SIZE = 50;
    @Autowired
    public void setPopulationGenerator(PopulationGenerationService populationGenerator) {
        this.populationGenerator = populationGenerator;
    }
    @Autowired
    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @Override
    public List<Activity> createSchedule(List<Activity> activities) {
        List<Individual> population = populationGenerator.generate(activities, POPULATION_SIZE);
        for (int i = 0; i < GENERATION_COUNT; i++) {
            //TODO:
            // selection (need of a fitness function for that),
            // crossover
            // mutation
            List<Individual> selectedPopulation = selectionService.select(population);

            // break if the fitness target was met
        }
        return null;
    }
}
