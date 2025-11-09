package controller;

import java.util.List;

import config.ScheduleConfig;
import model.Chromosome;
import model.Gene;
import service.InitialPopulationService;

public class ScheduleController {
	private final InitialPopulationService initialPopulationService;

	public ScheduleController(InitialPopulationService initialPopulationService) {
		this.initialPopulationService = initialPopulationService;
	}

	// 1. KHỞI TẠO N(100) QUẦN THỂ BAN ĐẦU
	public void initialPopulation(){
		int chromosomeSize = ScheduleConfig.CHROMOSOME_SIZE_INIT;
		List<Chromosome> chromosomes = initialPopulationService.createInitialPopulation(chromosomeSize);
        
        int index = 1;
        for (Chromosome chromosome : chromosomes) {
            System.out.println("\n========== CHROMOSOME " + index + " ==========\n");
            for (Gene gene : chromosome.getGenes()) {
            	initialPopulationService.printGene(gene);
            }
            index++;
        }
        
        System.out.println("Số lượng cá thể: " + chromosomeSize);
	}
}
