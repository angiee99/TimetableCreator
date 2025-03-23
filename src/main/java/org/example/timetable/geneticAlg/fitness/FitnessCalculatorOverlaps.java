package org.example.timetable.geneticAlg.fitness;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 *  Calculates fitness as the total number of overlapping activities
 */
@Service
public class FitnessCalculatorOverlaps implements FitnessCalculator {
    @Override
    public int fitness(Individual individual) {
        int fitness = 0;
        for(int i =1; i <= 7; i++ ) {
            // get the activities at that day
            List<Gene> activitiesByDay = Utils.getActivitiesByDay(individual, i);
            if (activitiesByDay.isEmpty()) continue;

            // sort by time
            List<Gene> activitiesByDaySorted = activitiesByDay.stream().sorted
                    (Comparator.comparing(o -> o.getActivity().getTimeslot().getStart())).toList();

            int overlapsCount = Utils.countOverlaps(activitiesByDaySorted);
            fitness = fitness + overlapsCount;
        }
        individual.setFitness(fitness);
        return fitness;
    }
}
