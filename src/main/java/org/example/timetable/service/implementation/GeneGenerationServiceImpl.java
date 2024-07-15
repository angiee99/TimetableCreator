package org.example.timetable.service.implementation;

import org.example.timetable.model.Activity;
import org.example.timetable.model.ActivityType;
import org.example.timetable.model.Gene;
import org.example.timetable.service.GeneGenerationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GeneGenerationServiceImpl implements GeneGenerationService {
    @Override
    public Gene generateGene(ActivityType type, List<Activity> activities) {
        List<Activity> activitiesByType = getActivitiesByType(type, activities);

        //select one random activity from activitiesByType
        Random random = new Random();
        int randomIndex = random.nextInt(activitiesByType.size());
        Gene gene = new Gene(activitiesByType.get(randomIndex));

        return gene;
    }

    private List<Activity> getActivitiesByType(ActivityType type, List<Activity> activities) {
        return activities.stream().filter(activity -> activity.getActivityType().equals(type)).toList();
    }
}
