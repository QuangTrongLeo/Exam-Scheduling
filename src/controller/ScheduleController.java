package controller;

import config.Config;
import model.Population;
import service.InitPopulationService;


public class ScheduleController {
	
	private final InitPopulationService initPopulationService; 

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    }

    // ===== 1. KHỞI TẠO QUẦN THỂ =====
    public Population initPopulation() {
    	return initPopulationService.initializePopulation(Config.POPULATION_SIZE);
    }
    
    // ===== 2. TÍNH FITNESS =====
    // 2.1. Tính fitness cho mỗi cá thể
    public double fitness() {
    	double fitness = 0;
    	return fitness;
    }
    
    // 2.2. Tính fitness cho tất cả cá thể
    
}
