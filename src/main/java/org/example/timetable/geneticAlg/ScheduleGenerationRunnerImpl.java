package org.example.timetable.geneticAlg;

import org.example.timetable.geneticAlg.fitness.FitnessCalculator;
import org.example.timetable.geneticAlg.generators.PopulationGenerator;
import org.example.timetable.geneticAlg.operators.Crossover;
import org.example.timetable.geneticAlg.operators.Mutation;
import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Generation;
import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleGenerationRunnerImpl implements ScheduleGenerationRunner {
    private PopulationGenerator populationGenerator;
    private FitnessCalculator fitnessCalculator;
    private Crossover crossover;
    private Mutation mutation;
    @Value("${ga.generationCount}")
    private int GENERATION_COUNT;

    @Value("${ga.populationSize}")
    private int POPULATION_SIZE;

    @Value("${ga.fitness.target}")
    private int FITNESS_TARGET;

    @Value("${ga.fitness.threshold}")
    private int FITNESS_THRESHOLD;

    @Autowired
    public void setPopulationGenerator(PopulationGenerator populationGenerator) {
        this.populationGenerator = populationGenerator;
    }
    @Autowired
    public void setFitnessCalculator(@Qualifier("fitnessCalculatorCombined") FitnessCalculator fitnessCalculator) {
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
        try{
            for (int i = 0; i < GENERATION_COUNT; i++) {
                // count the fitness for all individuals -> getBestIndividual will also do that (think about it)
                for(Individual individual : generation.getPopulation()){
                    fitnessCalculator.fitness(individual);
                }

                // get the best individual fitness value, if fitness target is met -> stop
                if(generation.getBestIndividual(fitnessCalculator).getFitness() <= FITNESS_TARGET){
                    break;
                }
                // crossover with selecting parents based on integrated selection method
                List<Individual> populationWithOffsprings = crossover.doCrossover(
                        generation.getPopulation());
                // mutation
                List<Individual> populationWithMutations = mutation.mutate(populationWithOffsprings, activities);
                generation = new Generation(new ArrayList<>(populationWithMutations)); // update current generation
            }

            // return if generation is empty
            if(generation.getPopulation().isEmpty()){
                throw new NoSolutionFoundException("The last generation is empty");
            }

            Individual bestIndividual;
            bestIndividual = generation.getBestIndividual(fitnessCalculator);
            if(bestIndividual.getFitness() > FITNESS_THRESHOLD) {
                throw new NoFitIndividualException("Final generation has no schedule without overlaps");
            }
            List<Activity> result = bestIndividual.getGenes().stream().map(Gene::getActivity).toList();
            return result;
        } catch (NoFitIndividualException e){
            throw new NoSolutionFoundException(
                    "No solution was found, exception: " + e.getLocalizedMessage(), e);
        }
    }
}
