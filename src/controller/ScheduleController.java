package controller;

import config.Config;
import model.Population;
import service.InitPopulationService;


public class ScheduleController {
	
	private final InitPopulationService initPopulationService; 

    public ScheduleController() {
    	this.initPopulationService = new InitPopulationService();
    }

    // 1. Khởi tạo quần thể
    public Population initPopulation() {
    	return initPopulationService.initializePopulation(Config.POPULATION_SIZE);
    }
}
