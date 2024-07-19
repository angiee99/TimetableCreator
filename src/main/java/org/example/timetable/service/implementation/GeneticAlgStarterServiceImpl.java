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
        Generation generation =  populationGenerator.generate(activities, POPULATION_SIZE);
        Generation previousPopulation = generation;

        for (int i = 0; i < GENERATION_COUNT; i++) {

            ArrayList<Individual> selectedPopulation = (ArrayList<Individual>) selectionService.select(
                    List.copyOf(generation.getPopulation()));// selection

            if(selectedPopulation.isEmpty()){ // no solution found
                generation = previousPopulation.withPopulation();
                break;
            }
            //TODO: break if the fitness target was met -> set the target, calculate the fitness of whole population
            // or if only 1 selected individual

            // crossover
            List<Individual> populationWithOffsprings =  crossoverService.crossover(List.copyOf(selectedPopulation), POPULATION_SIZE);
            // mutation
            List<Individual> populationWithMutations =  mutationService.mutate(List.copyOf(populationWithOffsprings), activities);

            previousPopulation = new Generation(selectedPopulation); // selected population
            generation = new Generation(new ArrayList<>(populationWithMutations));
        }
        //TODO: HERE to implement in Generation  this method like getBestIndividual
        Individual bestIndividual = chooseBestIndividual(generation.getPopulation());

        List<Activity> result = bestIndividual.getGenes().stream().map(Gene::getActivity).toList();
        return result;
    }

    //TODO: find the individual with best (lowest) fitness value
    //but after crossover the fitness is reset to 0 and not calculated,
    // fitness for now will be present only if we break from for cycle when fitness target is met
    private Individual chooseBestIndividual(List<Individual> population) {
        population = population.stream().filter(individual -> individual.getFitness()>=0).toList();
        if(population.isEmpty())
            return new Individual(); // change it to say No solution
        return population.get(0); // return with the lowest fitness value
    }
}
