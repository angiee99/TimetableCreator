package org.example.timetable.model;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    List<Gene> genes;
    int fitness;

    public void addGene(Gene gene){
        genes.add(gene);
    }
    public Individual(List<Gene> genes) {
        this.genes = genes;
    }

    public Individual() {
        genes = new ArrayList<>();
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
