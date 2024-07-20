package org.example.timetable.service.implementation;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.model.Timeslot;
import org.example.timetable.service.FitnessCalcService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;
@Service
public class FitnessCalcServiceImpl implements FitnessCalcService {
    // returns the fitness value
    // if it is negative, the individual id discarded
    // else the lesser the fitness is, the better
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

            // sum up the duration of all
            long sum = activitiesByDaySorted.stream().mapToLong(a -> a.getActivity().getDuration()).sum();

            // get the timespan (last end - first start)
            long timespan =  MINUTES.between(activitiesByDaySorted.get(0).getActivity().getTimeslot().getStart(),
                    activitiesByDaySorted.get(activitiesByDaySorted.size()-1).getActivity().getTimeslot().getEnd());

            int fitnessIndividual = (int) (timespan - sum);

            if(fitnessIndividual < 0) { // this individual has the overlap because NO BREAKS were detected
                fitness = fitnessIndividual;
                break;
            }

            // Double check for overlaps
            boolean hasOverlap = false;
            for (int j = 0; j < activitiesByDaySorted.size() - 1; j++) {
                Timeslot current = activitiesByDaySorted.get(j).getActivity().getTimeslot();
                Timeslot next = activitiesByDaySorted.get(j + 1).getActivity().getTimeslot();

                if (!current.getEnd().isBefore(next.getStart())) {
                    hasOverlap = true;
                    break; // there is an overlap
                }
            }
            // Have we detected an overlap in for cycle before?
            if(hasOverlap) {
                fitness = -1;
                break;
            }

            fitness += fitnessIndividual;
        }

        individual.setFitness(fitness);
        return fitness;
    }


    private List<Gene> getActivitiesByDay(Individual individual, int i) {
        return individual.getGenes().stream().filter(
                gene ->
                        gene.getActivity().getTimeslot().getDay()
                                .equals(DayOfWeek.of(i)))
                .toList();
    }
}
