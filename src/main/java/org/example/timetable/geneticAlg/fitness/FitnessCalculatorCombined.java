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
            int breaksLength = (int) countTotalBreakMinutes(activitiesByDaySorted);
            fitness = fitness + breaksLength * breakWeight;
        }
        individual.setFitness(fitness);
        return fitness;
    }

    private long countTotalBreakMinutes(List<Gene> activitiesSortedByStart) {
        if (activitiesSortedByStart.size() < 2) {
            return 0;
        }

        long totalBreakMinutes = 0;

        for (int i = 1; i < activitiesSortedByStart.size(); i++) {
            var previousEnd = activitiesSortedByStart.get(i - 1).getActivity().getTimeslot().getEnd();
            var currentStart = activitiesSortedByStart.get(i).getActivity().getTimeslot().getStart();

            long gap = MINUTES.between(previousEnd, currentStart);
            if (gap > 0) {
                totalBreakMinutes += gap;
            }
        }

        return (long) Math.floor(totalBreakMinutes / 60d);
    }
}
