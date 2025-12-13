package service;

import java.util.List;

import config.Config;
import model.Individual;

public class FitnessService {
	private final FitnessHardConstraintService fitnessHardConstraintService;
	private final FitnessSoftConstraintService fitnessSoftConstraintService;
	public FitnessService() {
		this.fitnessHardConstraintService = new FitnessHardConstraintService();
		this.fitnessSoftConstraintService = new FitnessSoftConstraintService();
	}
	
	public double fitness(Individual individual) {
    	double fitness = 0;
    	if(!fitnessHardConstraintService.checkHardConstraint(individual)) {
    		fitness = fitnessSoftConstraintService.fitness(individual);
    	} else {
    		fitness = Config.FITNESS_INVALID_SCORE;
    	}
    	return fitness;
    }
    
    public List<Individual> fitnessIndividuals(List<Individual> individuals) {
    	for (Individual individual : individuals) {
			individual.setFitness(fitness(individual));
		}
    	return individuals;
    }
}
