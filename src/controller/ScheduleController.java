package controller;

import java.util.List;

import config.Config;
import model.Individual;
import model.Population;
import service.CrossoverService;
import service.FitnessHardConstraintService;
import service.FitnessSoftConstraintService;
import service.GeneratedIndividualsPerGenerationService;
import service.InitPopulationService;
import service.MutationService;


public class ScheduleController {
	
	private Population population;
	
	private final InitPopulationService initPopulationService; 
	private final FitnessHardConstraintService fitnessHardConstraintService;
	private final FitnessSoftConstraintService fitnessSoftConstraintService;
	private final CrossoverService crossoverService;
	private final MutationService mutationService;
	private final GeneratedIndividualsPerGenerationService generatedIndividualsPerGenerationService;

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    	this.fitnessHardConstraintService = new FitnessHardConstraintService();
    	this.fitnessSoftConstraintService = new FitnessSoftConstraintService();
    	this.crossoverService = new CrossoverService();
    	this.mutationService = new MutationService();
    	this.generatedIndividualsPerGenerationService = new GeneratedIndividualsPerGenerationService();
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
    
    // ===== 3. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TÍCH LŨY =====
    public List<Individual> getAccumulatedIndividuals() {
        return getPopulation().getIndividuals();
    }

    // ===== 4. LAI TẠO =====
    public List<Individual> generateCrossover(List<Individual> individuals) {
        return crossoverService.offspringCrossoverIndivials(individuals);
    }

    // ===== 5. ĐỘT BIẾN =====
    public List<Individual> generateMutation(List<Individual> individuals){
    	return individuals;
    }

    // ===== 6. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TRONG 1 THẾ HỆ ======
    public List<Individual> getGeneratedIndividualsPerGeneration(List<Individual> individuals) {
        return generatedIndividualsPerGenerationService.getGeneratedIndividualsPerGeneration(individuals);
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
