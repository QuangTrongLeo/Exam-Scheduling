package controller;

import config.Config;
import model.Individual;
import model.Population;
import service.FitnessSoftConstraint;
import service.InitPopulationService;


public class ScheduleController {
	
	private Population population;
	private final InitPopulationService initPopulationService; 
	private final FitnessSoftConstraint fitnessService;

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    	this.fitnessService = new FitnessSoftConstraint();
    }

    // ===== 1. KHỞI TẠO QUẦN THỂ =====
    public Population initPopulation() {
    	population = initPopulationService.initializePopulation(Config.INIT_POPULATION_SIZE);
    	return population;
    }
    
    // ===== 2. TÍNH FITNESS =====
    // 2.1. Tính fitness cho mỗi cá thể
    public double fitness(Individual individual) {
    	return fitnessService.fitness(individual);
    }
    
    // 2.2. Tính fitness cho tất cả cá thể
    
}
