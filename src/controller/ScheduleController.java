package controller;

import java.util.List;

import config.Config;
import model.Individual;
import model.Population;
import service.FitnessHardConstraintService;
import service.InitPopulationService;


public class ScheduleController {
	
	private Population population;
	private final InitPopulationService initPopulationService; 
	private final FitnessHardConstraintService fitnessHardConstraintService;

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    	this.fitnessHardConstraintService = new FitnessHardConstraintService();
    	this.population = initPopulation();
    }

    // ===== 1. KHỞI TẠO QUẦN THỂ =====
    public Population initPopulation() {
        population = initPopulationService.initializePopulation(Config.INIT_POPULATION_SIZE);
        return population;
    }

    
    // ===== 2. TÍNH FITNESS =====
    // 2.1. Tính fitness cho mỗi cá thể
    public double fitness(Individual individual) {
    	double fitness = 0;
    	return fitness;
    }
    
    // 2.2. Tính fitness cho tất cả cá thể
    public Population fitnessIndividuals(Population population){
    	List<Individual> individuals = population.getIndividuals();
    	for (Individual individual : individuals) {
			individual.setFitness(fitness(individual));
		}
    	return population;
    }

	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}
    
}
