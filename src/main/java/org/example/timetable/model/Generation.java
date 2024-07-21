package org.example.timetable.model;

import org.example.timetable.service.FitnessCalcService;
import org.example.timetable.service.implementation.FitnessCalcServiceImpl;
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
        FitnessCalcService fitnessCalcService = new FitnessCalcServiceImpl();
        for(Individual individual : population){
            fitnessCalcService.fitness(individual);
        }
        List<Individual> sortedByFitness = new ArrayList<>(population.stream()
                .sorted(Comparator.comparing(Individual::getFitness)).toList());

        sortedByFitness.removeIf(individual -> individual.getFitness()<0);

        return sortedByFitness.get(0);
    }
}
