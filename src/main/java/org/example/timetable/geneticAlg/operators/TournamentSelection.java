package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Individual;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
@Service
public class TournamentSelection implements Selection{
    private final int tounamentSize = 2;
    @Override
    public Individual select(List<Individual> population) {
        Random random = new Random();
        List<Individual> preselected = new ArrayList<>();
        // select individuals for the tournament
        for (int i = 0; i < tounamentSize; i++) {
            int index = random.nextInt(population.size());
            preselected.add(population.get(index));
        }
        // choose the best individual from the tournament: the one with the lowest value of getFitness
        return preselected.stream()
                .min(Comparator.comparingInt(Individual::getFitness)).get();
    }
}
