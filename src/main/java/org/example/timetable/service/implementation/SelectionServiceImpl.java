package org.example.timetable.service.implementation;

import org.example.timetable.model.Individual;
import org.example.timetable.service.SelectionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectionServiceImpl implements SelectionService {

    @Override
    public List<Individual> select(List<Individual> population) {
        List<Individual> selectedIndividuals = new ArrayList<>();
        for(Individual individual : population){
            int fitnessValue = fitness(individual);
            if(fitnessValue >= 0){
                individual.setFitness(fitnessValue);
                selectedIndividuals.add(individual);
            }
        }
        // if too much left ->  add filter fitnessValue < 6
        return selectedIndividuals;
    }

    // returns the fitness value, if it is negative, the individual id discarded
    // else the lesser the fitness is, the better
    @Override
    public int fitness(Individual individual) {
        return 0;
    }
}
