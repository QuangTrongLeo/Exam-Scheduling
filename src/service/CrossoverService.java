package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Config;
import model.Gene;
import model.Individual;

public class CrossoverService {
    public List<Individual> crossover(Individual p1, Individual p2){
        List<Individual> individuals = new ArrayList<>();
        
        List<Gene> headP1 = getGeneSegment(p1, Config.CROSSOVER_60_PERCENT, true);
        List<Gene> headP2 = getGeneSegment(p2, Config.CROSSOVER_60_PERCENT, true);
        List<Gene> tailP1 = getGeneSegment(p1, Config.CROSSOVER_40_PERCENT, false);
        List<Gene> tailP2 = getGeneSegment(p2, Config.CROSSOVER_40_PERCENT, false);

        Individual offspringIndividual1 = createOffspringFromSegments(headP1, tailP2); 
        Individual offspringIndividual2 = createOffspringFromSegments(headP2, tailP1);
        
        individuals.add(offspringIndividual1);
        individuals.add(offspringIndividual2);
        return individuals;
    }

    private List<Gene> getGeneSegment(Individual individual, double percent, boolean isHead) {
        int geneSize = individual.getGenes().size();
        int cutoff = (int) (geneSize * percent);
        
        int startIndex = isHead ? 0 : cutoff;
        int endIndex = isHead ? cutoff : geneSize;
        
        List<Gene> segment = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            segment.add(individual.getGenes().get(i));
        }
        return segment;
    }

    private Individual createOffspringFromSegments(List<Gene> head, List<Gene> tail) {
        Individual offspring = new Individual();
        List<Gene> combinedGenes = new ArrayList<>();
        combinedGenes.addAll(head);
        combinedGenes.addAll(tail);
        
        offspring.setGenes(combinedGenes);
        return offspring;
    }
}