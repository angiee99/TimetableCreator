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

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Individual replaceGene(Gene geneToMutate, Gene newGene) {
        int indexGeneToMutate = genes.indexOf(geneToMutate);
        List<Gene> newGenes = new ArrayList<>();

        for (int i = 0; i < genes.size(); i++) {
            if(i == indexGeneToMutate){
                newGenes.add(newGene);
            }
            else newGenes.add(this.genes.get(i));
        }
        return new Individual(newGenes);
    }
}
