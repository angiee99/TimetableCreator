package org.example.timetable.model;

import org.example.timetable.geneticAlg.fitness.FitnessCalculator;
import org.example.timetable.model.exception.NoFitIndividualException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class Generation {
    private final ArrayList<Individual> population;

    public Generation(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * gets the best individual based on the fitness
     * @param fitnessCalculator the implementation of {@link FitnessCalculator} to be used
     * @return the individual with the lowest non-negative fitness value
     */
    public Individual getBestIndividual(FitnessCalculator fitnessCalculator){
        for(Individual individual : population){
            fitnessCalculator.fitness(individual);
        }
        List<Individual> sortedByFitness = new ArrayList<>(population.stream()
                .sorted(Comparator.comparing(Individual::getFitness)).toList());

        sortedByFitness.removeIf(individual -> individual.getFitness()<0);

        if(sortedByFitness.isEmpty()){
            throw new NoFitIndividualException("No individual with valid fitness found");
        }
        return sortedByFitness.get(0);
    }
}
