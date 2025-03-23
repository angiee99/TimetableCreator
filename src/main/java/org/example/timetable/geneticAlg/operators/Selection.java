package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;

import java.util.List;

public interface Selection {
    /**
     * @param population selecting from this population
     * @return 1 selected individual right for the crossover
     */
    Individual select(List<Individual> population) throws NoFitIndividualException;
}
