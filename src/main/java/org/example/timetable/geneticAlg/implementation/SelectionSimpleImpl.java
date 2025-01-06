package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.model.Individual;
import org.example.timetable.geneticAlg.FitnessCalculator;
import org.example.timetable.geneticAlg.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Selects any individual, whose fitness value is non-negative
 */
@Service
public class SelectionSimpleImpl implements Selection {
    private FitnessCalculator fitnessCalculator;
    @Autowired
    public void setFitnessCalculator(@Qualifier("fitnessCalculatorBreakSumImpl") FitnessCalculator fitnessCalculator) {
        this.fitnessCalculator = fitnessCalculator;
    }

    @Override
    public List<Individual> select(List<Individual> population) {
        List<Individual> selectedIndividuals = new ArrayList<>();
        for(Individual individual : population){
            int fitnessValue = fitnessCalculator.fitness(individual);
            individual.setFitness(fitnessValue); // this may be redundant (but test it)
            if(fitnessValue >= 0){
                selectedIndividuals.add(individual);
            }
        }
        // if too much left ->  add filter fitnessValue < 6(to minutes) hours of individual
        return selectedIndividuals;
    }
}
