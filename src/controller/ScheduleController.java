package controller;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import model.Individual;
import model.Population;
import service.AccumulateIndividualsService;
import service.CrossoverService;
import service.FitnessHardConstraintService;
import service.FitnessService;
import service.FitnessSoftConstraintService;
import service.GenerationAggregationService;
import service.InitPopulationService;
import service.MutationService;
import service.TheBestIndividualService;


public class ScheduleController {
	private final InitPopulationService initPopulationService; 
	private final FitnessService fitnessService;
	private final CrossoverService crossoverService;
	private final MutationService mutationService;
	private final GenerationAggregationService generationAggregationService;
	private final AccumulateIndividualsService accumulateIndividualsService;
	private final TheBestIndividualService theBestIndividualService;

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    	this.fitnessService = new FitnessService();
    	this.crossoverService = new CrossoverService();	
    	this.mutationService = new MutationService();
    	this.generationAggregationService = new GenerationAggregationService();
    	this.accumulateIndividualsService = new AccumulateIndividualsService();
    	this.theBestIndividualService = new TheBestIndividualService();
    }

    // ===== 1. KHỞI TẠO QUẦN THỂ =====
    public Population initPopulation() {
    	return initPopulationService.initPopulation(Config.INIT_POPULATION_SIZE);
    }
    
    // ===== 2. TÍNH FITNESS =====
    // 2.1. Tính fitness cho mỗi cá thể
    public double fitness(Individual individual) {
    	return fitnessService.fitness(individual);
    }
    
    // 2.2. Tính fitness cho tất cả cá thể
    public List<Individual> fitnessIndividuals(List<Individual> individuals) {
    	return fitnessService.fitnessIndividuals(individuals);
    }

    // ===== 3. LAI TẠO =====
    public List<Individual> crossover(Individual p1, Individual p2){
    	return crossoverService.crossover(p1, p2);
    }

    // ===== 4. ĐỘT BIẾN =====
    public Individual mutation(Individual individual){
    	return mutationService.mutate(individual);
    }

    // ===== 5. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TRONG 1 THẾ HỆ ======
    public List<Individual> aggregateGeneration(List<Individual> individuals){
    	return generationAggregationService.aggregateGeneration(individuals);
    }
    
    // ===== 6. DANH SÁCH TỔNG SỐ LƯỢNG CÁ THỂ TÍCH LŨY =====
    public List<Individual> accumulateIndividuals() {
    	return accumulateIndividualsService.accumulateIndividuals();
    }
    
    // ===== 7. CÁ THỂ TỐT NHẤT =====
    public Individual theBestIndividual() {
    	return theBestIndividualService.theBestIndividual();
    }
}
