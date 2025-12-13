package service;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import model.Individual;

public class AccumulateIndividualsService {
	private final InitPopulationService initPopulationService; 
	private final GenerationAggregationService generationAggregationService;
	private final FitnessService fitnessService;
	
	public AccumulateIndividualsService() {
		this.initPopulationService = new InitPopulationService();
		this.generationAggregationService = new GenerationAggregationService();
		this.fitnessService = new FitnessService();
	}
	
	public List<Individual> accumulateIndividuals() {
    	List<Individual> accumulatedIndividuals = new ArrayList<>();
        accumulatedIndividuals.addAll(initPopulationService.initPopulation(Config.INIT_POPULATION_SIZE).getIndividuals());

        for (int generation = 1; generation <= Config.GENERATION_COUNT; generation++) {
            List<Individual> nextGenerationIndividual = generationAggregationService.aggregateGeneration(accumulatedIndividuals);
            accumulatedIndividuals.addAll(nextGenerationIndividual);
        }

        return fitnessService.fitnessIndividuals(accumulatedIndividuals);
    }
}
