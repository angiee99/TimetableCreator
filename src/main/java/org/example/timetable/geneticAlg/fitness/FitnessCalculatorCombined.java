package org.example.timetable.geneticAlg.fitness;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class FitnessCalculatorCombined implements FitnessCalculator {
    @Value("${ga.fitness.weights.overlap}")
    private int overlapWeight;

    @Value("${ga.fitness.weights.breaks}")
    private int breakWeight;
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

            // Add overlaps
            int overlapsCount = Utils.countOverlaps(activitiesByDaySorted);
            fitness = fitness + overlapsCount * overlapWeight;

            // Add breaks
            int breaksLength = countBreaks(activitiesByDaySorted);
            fitness = fitness + breaksLength * breakWeight;
        }
        individual.setFitness(fitness);
        return fitness;
    }

    private int countBreaks(List<Gene> activitiesByDaySorted) {
        // sum up the duration of all activities in a day
        long sum = activitiesByDaySorted.stream().mapToLong(a -> a.getActivity().getDuration()).sum();

        // get the timespan (last end - first start)
        long timespan =  MINUTES.between(
                activitiesByDaySorted.getFirst().getActivity().getTimeslot().getStart(),
                activitiesByDaySorted.getLast().getActivity().getTimeslot().getEnd());

        return Math.round((timespan - sum) / 60f); // divide by 60 to return the hours
    }
}
