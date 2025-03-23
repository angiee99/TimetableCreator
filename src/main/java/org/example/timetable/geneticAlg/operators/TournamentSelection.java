package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TournamentSelection implements Selection{
    @Value("${ga.selection.tournament-size}")
    private int tournamentSize;
    @Override
    public Individual select(List<Individual> population) throws NoFitIndividualException {
        Random random = new Random();
        List<Individual> preselected = new ArrayList<>();
        // select individuals for the tournament
        for (int i = 0; i < tournamentSize; i++) {
            int index = random.nextInt(population.size());
            preselected.add(population.get(index));
        }
        // choose the best individual from the tournament: the one with the lowest value of fitness
        Optional<Individual> result = preselected.stream()
                .min(Comparator.comparingInt(Individual::getFitness));
        if(result.isPresent()) return result.get();
        else throw new NoFitIndividualException("Could not select the fit individual in in tournament selection.");
    }
}
