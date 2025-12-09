package controller;

import java.util.List;

import config.Config;
import model.Individual;
import model.Population;
import service.CrossoverService;
import service.FitnessHardConstraintService;
import service.FitnessSoftConstraintService;
import service.GenerationAggregationService;
import service.InitPopulationService;
import service.MutationService;


public class ScheduleController {
	
	private Population population;
	
	private final InitPopulationService initPopulationService; 
	private final FitnessHardConstraintService fitnessHardConstraintService;
	private final FitnessSoftConstraintService fitnessSoftConstraintService;
	private final CrossoverService crossoverService;
	private final MutationService mutationService;
	private final GenerationAggregationService generationAggregationService;

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    	this.fitnessHardConstraintService = new FitnessHardConstraintService();
    	this.fitnessSoftConstraintService = new FitnessSoftConstraintService();
    	this.crossoverService = new CrossoverService();	
    	this.mutationService = new MutationService();
    	this.generationAggregationService = new GenerationAggregationService();
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
    	if(!fitnessHardConstraintService.checkHardConstraint(individual)) {
    		fitness = fitnessSoftConstraintService.fitness(individual);
    	} else {
    		fitness = Config.FITNESS_INVALID_SCORE;
    	}
    	return fitness;
    }
    
    // 2.2. Tính fitness cho tất cả cá thể
    public List<Individual> fitnessIndividuals(List<Individual> individuals) {
    	for (Individual individual : individuals) {
			individual.setFitness(fitness(individual));
		}
    	return individuals;
    }

    // ===== 3. LAI TẠO =====
    public List<Individual> crossover(Individual p1, Individual p2){
    	return crossoverService.crossover(p1, p2);
    }

    // ===== 4. ĐỘT BIẾN =====
    public Individual mutation(Individual individual){
    	return individual;
    }

    // ===== 5. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TRONG 1 THẾ HỆ ======
    public List<Individual> aggregateGeneration(List<Individual> individuals){
    	return generationAggregationService.aggregateGeneration(individuals);
    }
    
    // ===== 6. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TÍCH LŨY =====
    public List<Individual> getAccumulatedIndividuals() {
        return getPopulation().getIndividuals();
    }

    public Population getPopulation() {
        if (population == null) {
            population = initPopulation();
        }
        return population;
    }

	public void setPopulation(Population population) {
		this.population = population;
	}
}
