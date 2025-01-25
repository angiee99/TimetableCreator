package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.Selection;
import org.example.timetable.model.Individual;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SelectionRouletteWheelImpl implements Selection{
    /**
     * @param population selecting from this population
     * @return 1 selected individual right for the crossover
     */
    @Override
    public List<Individual> select(List<Individual> population) {
        List<Individual> selected = new ArrayList<>();
        int epsilon = 1;
        double totalWeight = population.stream()
                .map(Individual::getFitness)
                .mapToDouble(f -> 1.0 / (f + epsilon)) // mapping to probabilities of being selected
                .reduce(0, Double::sum);

        // Generate a random number between 0 and total fitness
        Random random = new Random();

        double randomValue = random.nextDouble() * totalWeight;

        // Select an individual based on the inverted fitness proportion
        double runningSum = 0.0;
        for (Individual entry : population) {
            double weight = 1.0 / (entry.getFitness() + epsilon);
            runningSum += weight;
            if (runningSum >= randomValue) {
                selected.add(entry);
                return selected;
            }
        }
        return selected; // Shouldn't reach here if totalWeight is calculated correctly
    }
}
