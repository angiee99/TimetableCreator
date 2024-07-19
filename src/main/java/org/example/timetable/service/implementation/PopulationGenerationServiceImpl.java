package org.example.timetable.service.implementation;

import org.example.timetable.model.*;
import org.example.timetable.service.GeneGenerationService;
import org.example.timetable.service.PopulationGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PopulationGenerationServiceImpl implements PopulationGenerationService {
    GeneGenerationService geneGenerator;
    @Override
    public Generation generate(List<Activity> activities, int populationSize) {
        Set<ActivityType> allActivityTypes = getAllUniqueActivityTypes(activities);

        ArrayList<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Individual chromosome = new Individual();

            for(ActivityType type: allActivityTypes){
                Gene gene = geneGenerator.generateGene(type, activities);
                chromosome.addGene(gene);
            }

            individuals.add(chromosome);
        }

        return new Generation(individuals);
    }

    private Set<ActivityType> getAllUniqueActivityTypes(List<Activity> activities) {
        Set<ActivityType> activityTypes = new HashSet<>();
        for(Activity activity : activities){
            activityTypes.add(activity.getActivityType());
        }
        return activityTypes;
    }

    @Autowired
    public void setGeneGenerator(GeneGenerationService geneGenerator) {
        this.geneGenerator = geneGenerator;
    }
}
