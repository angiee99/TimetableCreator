package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Activity;
import org.example.timetable.model.Gene;
import org.example.timetable.model.Individual;
import org.example.timetable.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RandomResettingMutation implements Mutation {
    private static final Random rand = new Random();

    @Value("${ga.rate.mutation}")
    private double mutationRate; // % of individuals to undergo mutation
    @Override
    public List<Individual> mutate(List<Individual> population, List<Activity> activities) {
        List<Individual> mutatedPopulation = new ArrayList<>(population);
        int mutationNumber = (int) Math.round(mutationRate * population.size()); // number of individuals to mutate
        Set<Integer> mutationIndices = new HashSet<>();
        // Select unique random indices of individuals for mutation
        while (mutationIndices.size() < mutationNumber) {
            mutationIndices.add(rand.nextInt(population.size()));
        }
        for (int index : mutationIndices) {
            Individual individual = mutatedPopulation.get(index);

            // Randomly select a gene to mutate
            int geneIndex = rand.nextInt(individual.getGenes().size());
            Gene geneToMutate = individual.getGenes().get(geneIndex);

            // select all Activities with the same ActivityType as Gene from initialPopulation
            List<Activity> activitiesByType = Utils.getActivitiesByType(
                    geneToMutate.getActivity(), List.copyOf(activities));

            if(!activitiesByType.isEmpty()){
                // randomly select one activity to change the Gene to be mutated
                Activity mutatedActivity = activitiesByType.get(rand.nextInt(activitiesByType.size()));

                // change the Gene in individual
                Gene newGene = geneToMutate.withActivity(mutatedActivity);
                Individual mutatedIndividual = individual.replaceGene(geneToMutate, newGene);

                // Update population with the mutated individual
                mutatedPopulation.set(index, mutatedIndividual);
            }
        }
        return mutatedPopulation;
    }
}
