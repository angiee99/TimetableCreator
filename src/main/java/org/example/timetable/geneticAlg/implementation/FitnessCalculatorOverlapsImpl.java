package org.example.timetable.geneticAlg.implementation;

import org.example.timetable.geneticAlg.FitnessCalculator;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.model.Timeslot;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;

/**
 *  Calculates fitness as the total number of overlapping activities
 */
@Service
public class FitnessCalculatorOverlapsImpl implements FitnessCalculator {
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

            int overlapsCount = countOverlaps(activitiesByDaySorted);
            fitness = fitness + overlapsCount;
        }
        return fitness;
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
