package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Individual;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RouletteWheelSelection implements Selection{
    @Override
    public Individual select(List<Individual> population) {
        int epsilon = 1;
        double totalWeight = population.stream()
                .map(Individual::getFitness)
                .mapToDouble(f -> 1.0 / (f + epsilon)) // mapping to probabilities of being selected
                .reduce(0, Double::sum);

        Random random = new Random();// generate a random number between 0 and total fitness
        double randomValue = random.nextDouble() * totalWeight;

        // Select based on inverted fitness proportion
        double runningSum = 0.0;
        for (Individual individual : population) {
            double weight = 1.0 / (individual.getFitness() + epsilon);
            runningSum += weight;
            if (runningSum >= randomValue) {
                return individual;
            }
        }
        return new Individual(); // Shouldnt reach here if totalWeight is calculated correctly
    }
}
