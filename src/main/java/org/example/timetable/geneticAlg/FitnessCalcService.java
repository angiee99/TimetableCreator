package org.example.timetable.geneticAlg;

import org.example.timetable.model.Individual;

public interface FitnessCalcService {
    int fitness(Individual individual);
}
