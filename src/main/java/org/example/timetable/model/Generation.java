package org.example.timetable.model;

import org.example.timetable.model.exception.NoFitIndividualException;
import org.example.timetable.geneticAlg.FitnessCalculator;
import org.example.timetable.geneticAlg.implementation.FitnessCalculatorBreakSumImpl;
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
    public Generation copyWithPopulation(){
        return new Generation(population);
    }
    public Individual getBestIndividual(){
        FitnessCalculator fitnessCalculator = new FitnessCalculatorBreakSumImpl();
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
