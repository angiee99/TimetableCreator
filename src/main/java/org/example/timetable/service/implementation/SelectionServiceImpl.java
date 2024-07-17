package org.example.timetable.service.implementation;

import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.service.SelectionService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SelectionServiceImpl implements SelectionService {

    @Override
    public List<Individual> select(List<Individual> population) {
        List<Individual> selectedIndividuals = new ArrayList<>();
        for(Individual individual : population){
            int fitnessValue = fitness(individual);
            if(fitnessValue >= 0){
                individual.setFitness(fitnessValue);
                selectedIndividuals.add(individual);
            }
        }
        // if too much left ->  add filter fitnessValue < 6
        return selectedIndividuals;
    }

    // returns the fitness value, if it is negative, the individual id discarded
    // else the lesser the fitness is, the better
    @Override
    public int fitness(Individual individual) {
        int fitness = 0;
        long sum = 0;
        int timespan = 0;
        // for each day of week
        for(int i =1; i <= 7; i++ ){
            // get the activities at that day
            List<Gene> activitiesByDay = getActivitiesByDay(individual, i);
            // sort by time
            activitiesByDay = activitiesByDay.stream().sorted
                    (Comparator.comparing(o -> o.getActivity().getTimeslot().getStart())).toList();
            // sum up the duration of all
            sum = activitiesByDay.stream().mapToLong(a -> a.getActivity().getDuration()).sum();
            // get the timespan (last end - first start)


        }
        // for each day:
            // sort by time
            // sum up the duration of all
            // TODO: get the timespan (last end - first start)
            //  calculate value: timespan - sum
            // if < 0 break and return the value
            // else fitness += value
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
