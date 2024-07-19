package org.example.timetable.service.implementation;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
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

        // for each day:
        // sort by time
        // sum up the duration of all
        // get the timespan (last end - first start)
        // calculate value: timespan - sum
        // if < 0 break and return the value
        // else fitness += value
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

            if(fitnessIndividual < 0) { // this individual has the overlap
                fitness = fitnessIndividual;
                individual.setFitness(fitness); // this looks redundant
                break;
            };
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
