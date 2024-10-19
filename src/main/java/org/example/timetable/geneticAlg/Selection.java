package org.example.timetable.geneticAlg;

import org.example.timetable.model.Individual;

import java.util.List;

public interface Selection {
    List<Individual> select(List<Individual> population);
}
