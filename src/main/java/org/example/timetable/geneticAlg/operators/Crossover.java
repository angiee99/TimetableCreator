package org.example.timetable.geneticAlg.operators;

import org.example.timetable.model.Individual;
import org.example.timetable.model.exception.NoFitIndividualException;

import java.util.List;

public interface Crossover {
    List<Individual> doCrossover(List<Individual> parentPopulation) throws NoFitIndividualException;
}
