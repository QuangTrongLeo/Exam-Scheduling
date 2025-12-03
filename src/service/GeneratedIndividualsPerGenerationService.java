package service;

import java.util.ArrayList;
import java.util.List;

import controller.ScheduleController;
import model.Individual;

public class GeneratedIndividualsPerGenerationService {
	
	private ScheduleController controller;
	
	public List<Individual> getGeneratedIndividualsPerGeneration(List<Individual> individuals) {
        List<Individual> result = new ArrayList<>();

        result.addAll(controller.generateCrossover(individuals));
        result.addAll(controller.generateMutation(individuals));

        return result;
    }
}
