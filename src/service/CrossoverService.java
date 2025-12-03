package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Config;
import model.Gene;
import model.Individual;

public class CrossoverService {

    private final Random random = new Random();

    public Individual selectParent(List<Individual> individuals) {
        return individuals.get(random.nextInt(individuals.size()));
    }
    
    public List<Individual> createOffspringPopulation(List<Individual> population) {
        int populationSize = population.size();
        List<Individual> offspring = new ArrayList<>();

        for (int i = 0; i < populationSize / 2; i++) {
            Individual p1 = selectParent(population);
            Individual p2 = selectParent(population);

            List<Individual> children = crossover(p1, p2);
            offspring.add(children.get(0));
            offspring.add(children.get(1));
        }

        return offspring;
    }

    public List<Individual> crossover(Individual p1, Individual p2){
    	List<Individual> individuals = new ArrayList<>();
    	Individual individual1 = crossover60_40(p1, p2);
    	Individual individual2 = crossover40_60(p1, p2);
    	individuals.add(individual1);
    	individuals.add(individual2);
    	return individuals;
    }
    
    private Individual crossover60_40(Individual p1, Individual p2) {
    	int geneSize = p1.getGenes().size();
    	Individual individual = new Individual();
    	List<Gene> genes = new ArrayList<>();
        for (int i = 0; i < geneSize; i++) genes.add(null);
        individual.setGenes(genes);
    	
    	int cutoff = (int) (geneSize * Config.CROSSOVER_60_PERCENT);
    	for (int i = 0; i < geneSize; i++) {
			if (i < cutoff) {
				individual.getGenes().set(i, p1.getGenes().get(i));
			} else {
				individual.getGenes().set(i, p2.getGenes().get(i));
			}
		}
    	return individual;
    }
    
    private Individual crossover40_60(Individual p1, Individual p2) {
    	int geneSize = p1.getGenes().size();
        Individual individual = new Individual();
        List<Gene> genes = new ArrayList<>();
        for (int i = 0; i < geneSize; i++) genes.add(null);
        individual.setGenes(genes);
    	int cutoff = (int) (geneSize * Config.CROSSOVER_40_PERCENT);
    	for (int i = 0; i < geneSize; i++) {
			if (i < cutoff) {
				individual.getGenes().set(i, p1.getGenes().get(i));
			} else {
				individual.getGenes().set(i, p2.getGenes().get(i));
			}
		}
    	return individual;
    }
}
