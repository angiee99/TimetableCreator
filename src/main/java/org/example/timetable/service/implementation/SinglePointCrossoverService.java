package org.example.timetable.service.implementation;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.service.CrossoverService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SinglePointCrossoverService implements CrossoverService {
    private static final Random rand = new Random();
    @Override
    public List<Individual> crossover(List<Individual> parentPopulation, int populationSize) {
        List<Individual> newPopulation = new ArrayList<>(); // here possible to add elite individuals from parents
        for (int i = 0; i < populationSize; i+=2) {
            // Randomly choose 2 parents from parentPopulation
            Individual parent1 = parentPopulation.get(rand.nextInt(parentPopulation.size()));
            Individual parent2 = parentPopulation.get(rand.nextInt(parentPopulation.size()));

            // Perform single-point crossover
            Individual[] offsprings = singlePointCrossover(parent1, parent2);

            // Add 2 new offsprings to newPopulation
            newPopulation.add(offsprings[0]);
            newPopulation.add(offsprings[1]);
        }
        return newPopulation;
    }

    private Individual[] singlePointCrossover(Individual parent1, Individual parent2) {
        int length = parent1.getGenes().size();
        int crossoverPoint = rand.nextInt(length);
        Individual child1 = new Individual();
        Individual child2 = new Individual();

        for (int i = 0; i < length; i++) {
            if (i < crossoverPoint) {
                child1.addGene(parent1.getGenes().get(i));
                child2.addGene(parent2.getGenes().get(i));
            } else {
                child1.addGene(parent2.getGenes().get(i));
                child2.addGene(parent1.getGenes().get(i));
            }
        }
        return new Individual[]{child1, child2};
    }
}
