package org.example.timetable.service;

import org.example.timetable.model.Individual;

import java.util.List;

public interface SelectionService {
    List<Individual> select(List<Individual> population);
}
