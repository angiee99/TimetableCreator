package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.service.MutationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MutationServiceImpl implements MutationService {
    private static final Random rand = new Random();
    @Override
    public List<Individual> mutate(List<Individual> population, List<Activity> activities) {
        List<Individual> mutatedPopulation = new ArrayList<>();
        for(Individual individual : population){
            // randomly generate the index for gene to be mutated
            int index = rand.nextInt(individual.getGenes().size());
            // -> TODO: Add the probability based on available activities later

            // get the Gene to be mutated
            Gene geneToMutate = individual.getGenes().get(index);

            // select all Activities with the same ActivityType as Gene from initialPopulation
            List<Activity> activitiesByType = getActivitiesByType(
                    geneToMutate.getActivity(), List.copyOf(activities));

            if(!activitiesByType.isEmpty()){
                // randomly select one activity to change the Gene to be mutated
                Activity mutatedActivity = activitiesByType.get(rand.nextInt(activitiesByType.size()));

                // change the Gene in individual
                Gene newGene = geneToMutate.withActivity(mutatedActivity);

                Individual mutatedIndividual = individual.replaceGene(geneToMutate, newGene);
                mutatedPopulation.add(mutatedIndividual);
            }
            else mutatedPopulation.add(individual);
        }

        return mutatedPopulation;
    }
    private List<Activity> getActivitiesByType(Activity activityToChange, List<Activity> activities) {
        return activities.stream().filter(activity
                -> activity.getActivityType().equals(activityToChange.getActivityType())
                && !activity.equals(activityToChange)).toList(); // exclude the same activity we want to change in the list
    }
}
