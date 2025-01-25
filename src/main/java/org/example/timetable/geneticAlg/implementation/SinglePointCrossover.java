package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.Selection;
import org.example.timetable.model.Individual;
import org.example.timetable.geneticAlg.Crossover;
import org.example.timetable.model.exception.NoSolutionFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SinglePointCrossover implements Crossover {
    private Selection selection;
    private static final Random rand = new Random();
    @Autowired
    public void setSelection(@Qualifier("selectionRouletteWheelImpl") Selection selection) {
        this.selection = selection;
    }

    @Override
    public List<Individual> doCrossover(List<Individual> parentPopulation, int populationSize) {
        List<Individual> newPopulation = new ArrayList<>(); // here possible to add elite individuals from parents
        for (int i = 0; i < populationSize; i+=2) {
            // Select 2 parents from parentPopulation
            Individual parent1 = selection.select(parentPopulation).get(0);
            Individual parent2 = selection.select(parentPopulation).get(0);

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
