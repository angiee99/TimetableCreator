package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.*;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Generation;
import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneticAlgStarterServiceImpl implements GeneticAlgStarterService {
    private PopulationGenerator populationGenerator;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private final int GENERATION_COUNT = 50; // 100-200
    private final int POPULATION_SIZE = 10; // 50
    private final int FITNESS_TARGET = 250; // 200 minutes of breaks between classes per week -> 3hours 20minutes
    private int RETRY_COUNT = 10; // 50
    @Autowired
    public void setPopulationGenerator(PopulationGenerator populationGenerator) {
        this.populationGenerator = populationGenerator;
    }
    @Autowired
    public void setSelectionService(Selection selection) {
        this.selection = selection;
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
        Generation selectedFromPreviousGeneration = generation.copyWithPopulation();

        for (int i = 0; i < GENERATION_COUNT; i++) {

            ArrayList<Individual> selectedPopulation = (ArrayList<Individual>) selection.select(
                    List.copyOf(generation.getPopulation()));// selection

            if(selectedPopulation.isEmpty()){ // no solution found in this iteration (think ab this case, if it breaks all the iteration)
                selectedPopulation = selectedFromPreviousGeneration.getPopulation();
//                if(RETRY_COUNT > 0){
//                    generation = populationGenerator.generate(activities, POPULATION_SIZE);
//                    RETRY_COUNT -= 1;
//                    continue;
//                }
//                else{
//                    generation = selectedFromPreviousGeneration.copyWithPopulation();
//                    break;
//                }
            }
            else{
                // what if only 1 selected?
                selectedFromPreviousGeneration = new Generation(selectedPopulation); //save the selected population

                // if fitness target is met, or it is the last iteration -> break with new selected individuals
                if((!selectedFromPreviousGeneration.getPopulation().isEmpty() &&
                        selectedFromPreviousGeneration.getBestIndividual().getFitness() <= FITNESS_TARGET)
                        || i == GENERATION_COUNT -1) {
                    generation = selectedFromPreviousGeneration.copyWithPopulation();
                    break;
                }
            }

            // crossover
            List<Individual> populationWithOffsprings =  crossover.doCrossover(
                    selectedPopulation, POPULATION_SIZE);
            // mutation
            List<Individual> populationWithMutations =  mutation.mutate(populationWithOffsprings, activities);

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
