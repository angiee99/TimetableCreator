package org.example.timetable.model;

import java.util.ArrayList;

public class Generation {
    private final ArrayList<Individual> population;

    public Generation(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }
    public Generation withPopulation(){
        return new Generation(population);
    }
}
