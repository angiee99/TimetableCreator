package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.*;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Generation;
import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneticAlgStarterServiceImpl implements GeneticAlgStarterService {
    private PopulationGenerator populationGenerator;
    private Selection selection;
    private FitnessCalculator fitnessCalculator;
    private Crossover crossover;
    private Mutation mutation;
    private final int GENERATION_COUNT = 100; // 100-200
    private final int POPULATION_SIZE = 30; // 50
    private final int FITNESS_TARGET = 250; // 200 minutes of breaks between classes per week -> 3hours 20minutes
    @Autowired
    public void setPopulationGenerator(PopulationGenerator populationGenerator) {
        this.populationGenerator = populationGenerator;
    }
    @Autowired
    public void setSelectionService(Selection selection) {
        this.selection = selection;
    }
    @Autowired
    public void setFitnessCalculator(@Qualifier("fitnessCalculatorOverlapsImpl") FitnessCalculator fitnessCalculator) {
        this.fitnessCalculator = fitnessCalculator;
    }
    @Autowired
    public void setCrossover(Crossover crossover) {
        this.crossover = crossover;
    }
    @Autowired
    public void setMutationService(Mutation mutation) {
        this.mutation = mutation;
    }

    @Override
    public List<Activity> createSchedule(List<Activity> activities) throws NoSolutionFoundException{
        Generation generation = populationGenerator.generate(activities, POPULATION_SIZE);
        for (int i = 0; i < GENERATION_COUNT; i++) {

           // count the fitness for all individuals
            for(Individual individual : generation.getPopulation()){
                individual.setFitness(fitnessCalculator.fitness(individual));
            }

            // crossover
            // todo: integrate selection inside the crossover (choose each 2 parents)
            List<Individual> populationWithOffsprings = crossover.doCrossover(
                    generation.getPopulation(), POPULATION_SIZE);
            // mutation
            List<Individual> populationWithMutations = mutation.mutate(populationWithOffsprings, activities);

            generation = new Generation(new ArrayList<>(populationWithMutations)); // update current generation
        }

        // return if generation is empty
        if(generation.getPopulation().isEmpty()){
            System.out.println("No solution was found, the last generation is empty.");
            throw new NoSolutionFoundException("No solution was found, the last generation is empty");
        }

        Individual bestIndividual;
        try{
            bestIndividual = generation.getBestIndividual();
            List<Activity> result = bestIndividual.getGenes().stream().map(Gene::getActivity).toList();
            return result;
        } catch (NoFitIndividualException e){
            System.out.println("No solution was found, exception:" + e.getLocalizedMessage());
            throw new NoSolutionFoundException(
                    "No solution was found, exception:" + e.getLocalizedMessage(), e);
        }
    }
}
