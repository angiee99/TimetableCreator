package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Individual;
import org.example.timetable.service.CrossoverService;
import org.example.timetable.service.GeneticAlgStarterService;
import org.example.timetable.service.PopulationGenerationService;
import org.example.timetable.service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneticAlgStarterServiceImpl implements GeneticAlgStarterService {
    private PopulationGenerationService populationGenerator;
    private SelectionService selectionService;
    private CrossoverService crossoverService;
    private final int GENERATION_COUNT = 50; // 100-200
    private final int POPULATION_SIZE = 10; // 50
    @Autowired
    public void setPopulationGenerator(PopulationGenerationService populationGenerator) {
        this.populationGenerator = populationGenerator;
    }
    @Autowired
    public void setSelectionService(SelectionService selectionService) {
        this.selectionService = selectionService;
    }
    @Autowired
    public void setCrossoverService(CrossoverService crossoverService) {
        this.crossoverService = crossoverService;
    }

    @Override
    public List<Activity> createSchedule(List<Activity> activities) {
        List<Individual> population = populationGenerator.generate(activities, POPULATION_SIZE);
        for (int i = 0; i < GENERATION_COUNT; i++) {

            List<Individual> selectedPopulation = selectionService.select(population);// selection

            if(selectedPopulation.isEmpty()){ // no solution found
                return new ArrayList<>();
            }
            //TODO: break if the fitness target was met -> set the target, calculate the fitness of whole population

            //TODO:
            // crossover
            // mutation
            List<Individual> populationWithOffsprings = crossoverService.crossover(selectedPopulation, POPULATION_SIZE);
        }
        return null;
    }
}
