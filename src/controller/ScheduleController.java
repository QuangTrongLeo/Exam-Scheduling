package controller;

import java.util.ArrayList;
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
	private List<Individual> accumulatedIndividuals = new ArrayList<>();
	
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
    public List<Individual> accumulateIndividuals() {
    	accumulatedIndividuals.clear();
        Population population = initPopulation();
        List<Individual> initIndividuals = population.getIndividuals();
        accumulatedIndividuals.addAll(initIndividuals);

        for (int generation = 1; generation <= Config.GENERATION_COUNT; generation++) {
            List<Individual> nextGenerationIndividual = aggregateGeneration(accumulatedIndividuals);
            accumulatedIndividuals.addAll(nextGenerationIndividual);
        }

        return fitnessIndividuals(accumulatedIndividuals);
    }
    
    // ===== 7. CÁ THỂ TỐT NHẤT =====
    public Individual theBestIndividual() {
        List<Individual> individuals = accumulateIndividuals();
        Individual theBestIndividual = individuals.get(0);
        int bestIndex = 0;

        for (int i = 1; i < individuals.size(); i++) {
            Individual currentIndividual = individuals.get(i);
            if (currentIndividual.getFitness() > theBestIndividual.getFitness()) {
            	theBestIndividual = currentIndividual;
                bestIndex = i;
            }
        }

        System.out.println("----- Cá thể " + bestIndex + " là cá thể tốt nhất -----");
        System.out.println("----- Cá thể này có fitness: " + theBestIndividual.getFitness() + " -----");
        return theBestIndividual;
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
