package org.example.timetable.geneticAlg;

import org.example.timetable.model.Activity;
import org.example.timetable.model.ActivityType;
import org.example.timetable.model.Gene;

import java.util.List;

public interface GeneGenerator {
    Gene generateGene(ActivityType type, List<Activity> activities);
}
