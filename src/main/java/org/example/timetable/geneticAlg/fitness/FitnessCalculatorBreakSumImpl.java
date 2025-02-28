package org.example.timetable.geneticAlg.fitness;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.model.Timeslot;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Calculates fitness as the sum of breaks between activities (the bigger value, the worse)
 * However, if any activities overlap, the fitness value returned is -1
 */
@Service
public class FitnessCalculatorBreakSumImpl implements FitnessCalculator {
    /**
     * returns the fitness value
     * if it is negative, the individual fitness is to be discarded
     * else the lesser the fitness is, the better
     */

    @Override
    public int fitness(Individual individual) {
        int fitness = 0;
        for(int i =1; i <= 7; i++ ){
            // get the activities at that day
            List<Gene> activitiesByDay = getActivitiesByDay(individual, i);
            if(activitiesByDay.isEmpty()) continue;

            // sort by time
            List<Gene> activitiesByDaySorted = activitiesByDay.stream().sorted
                    (Comparator.comparing(o -> o.getActivity().getTimeslot().getStart())).toList();

            // Check for overlaps (mb add calculation of timespan here to reduce the time)
            boolean hasOverlap = hasOverlaps(activitiesByDaySorted);
            if (hasOverlap) {
                fitness = -1;
                break;
            }

            // Calculate the fitness value: sum up the duration of all activities in a day
            long sum = activitiesByDaySorted.stream().mapToLong(a -> a.getActivity().getDuration()).sum();

            // get the timespan (last end - first start)
            long timespan =  MINUTES.between(activitiesByDaySorted.get(0).getActivity().getTimeslot().getStart(),
                    activitiesByDaySorted.get(activitiesByDaySorted.size()-1).getActivity().getTimeslot().getEnd());

            int fitnessIndividual = (int) (timespan - sum);

            if(fitnessIndividual < 0) { // this individual has the overlap because NO BREAKS were detected
                fitness = fitnessIndividual;
                break;
            }

            fitness += fitnessIndividual;
        }

        individual.setFitness(fitness);
        return fitness;
    }

    private boolean hasOverlaps(List<Gene> activities){
        for (int j = 0; j < activities.size() - 1; j++) {
            Timeslot current = activities.get(j).getActivity().getTimeslot();
            Timeslot next = activities.get(j + 1).getActivity().getTimeslot();

            if (current.getEnd().isAfter(next.getStart())) {
                return true;// there is an overlap
            }
        }
        return false;
    }

    private List<Gene> getActivitiesByDay(Individual individual, int i) {
        return individual.getGenes().stream().filter(
                gene ->
                        gene.getActivity().getTimeslot().getDay()
                                .equals(DayOfWeek.of(i)))
                .toList();
    }
}
