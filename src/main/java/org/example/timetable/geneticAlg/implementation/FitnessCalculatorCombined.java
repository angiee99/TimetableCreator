package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.FitnessCalculator;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.model.Timeslot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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
            List<Gene> activitiesByDay = getActivitiesByDay(individual, i);
            if (activitiesByDay.isEmpty()) continue;

            // sort by time
            List<Gene> activitiesByDaySorted = activitiesByDay.stream().sorted
                    (Comparator.comparing(o -> o.getActivity().getTimeslot().getStart())).toList();

            // Add overlaps
            int overlapsCount = countOverlaps(activitiesByDaySorted);
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
        long timespan =  MINUTES.between(activitiesByDaySorted.get(0).getActivity().getTimeslot().getStart(),
                activitiesByDaySorted.get(activitiesByDaySorted.size()-1).getActivity().getTimeslot().getEnd());

        return (int) (timespan - sum);
    }

    private int countOverlaps(List<Gene> activities) {
        int overlapCount = 0;
        for (int j = 0; j < activities.size() - 1; j++) {
            Timeslot a = activities.get(j).getActivity().getTimeslot();
            Timeslot b = activities.get(j + 1).getActivity().getTimeslot();
            if (a.getEnd().isAfter(b.getStart())) {
                overlapCount++; // add an overlap
            }
        }
        return overlapCount;
    }

    private List<Gene> getActivitiesByDay(Individual individual, int i) {
        return individual.getGenes().stream().filter(
                        gene ->
                                gene.getActivity().getTimeslot().getDay()
                                        .equals(DayOfWeek.of(i)))
                .toList();
    }

}
