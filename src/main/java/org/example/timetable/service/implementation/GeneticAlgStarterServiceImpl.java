package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Generation;
import org.example.timetable.model.Individual;
import org.example.timetable.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneticAlgStarterServiceImpl implements GeneticAlgStarterService {
    private PopulationGenerationService populationGenerator;
    private SelectionService selectionService;
    private CrossoverService crossoverService;
    private MutationService mutationService;
    private final int GENERATION_COUNT = 50; // 100-200
    private final int POPULATION_SIZE = 10; // 50
    private final int FITNESS_TARGET = 250; // 200 minutes of breaks between classes per week -> 3hours 20minutes
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
    @Autowired
    public void setMutationService(MutationService mutationService) {
        this.mutationService = mutationService;
    }

    @Override
    public List<Activity> createSchedule(List<Activity> activities) {
        Generation generation = populationGenerator.generate(activities, POPULATION_SIZE);
        Generation selectedFromPreviousGeneration = generation;

        for (int i = 0; i < GENERATION_COUNT; i++) {

            ArrayList<Individual> selectedPopulation = (ArrayList<Individual>) selectionService.select(
                    List.copyOf(generation.getPopulation()));// selection

            if(selectedPopulation.isEmpty()){ // no solution found
                generation = selectedFromPreviousGeneration.copyWithPopulation();
                break;
            }

            selectedFromPreviousGeneration = new Generation(selectedPopulation); //save the selected population

            // what if only 1 selected?

            // if fitness target is met, or it is the last iteration -> break with new selected individuals
            if(selectedFromPreviousGeneration.getBestIndividual().getFitness() <= FITNESS_TARGET
                || i == GENERATION_COUNT -1){
                generation = selectedFromPreviousGeneration.copyWithPopulation();
                break;
            }

            // crossover
            List<Individual> populationWithOffsprings =  crossoverService.crossover(selectedPopulation, POPULATION_SIZE);
            // mutation
            List<Individual> populationWithMutations =  mutationService.mutate(populationWithOffsprings, activities);

            generation = new Generation(new ArrayList<>(populationWithMutations)); // update current generation
        }

        Individual bestIndividual = generation.getBestIndividual();
        List<Activity> result = bestIndividual.getGenes().stream().map(Gene::getActivity).toList();
        return result;
    }

}
