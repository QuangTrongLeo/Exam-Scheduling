package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import model.Population;
import print.SchedulePrint;
import service.GenerationAggregationService;

public class GenerationAggregationMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		GenerationAggregationService service = new GenerationAggregationService();
		SchedulePrint print = new SchedulePrint();
		Population population = controller.initPopulation();
		
		List<Individual> individuals = population.getIndividuals();
		List<Individual> offspringCrossoverIndivials = service.offspringCrossoverIndivials(individuals);
		List<Individual> offspringMutationIndivials = service.offspringMutationIndivials(individuals);
		List<Individual> aggregateGeneration = controller.aggregateGeneration(individuals);
		
		List<Individual> offspringCrossoverFitnessIndivials = controller.fitnessIndividuals(offspringCrossoverIndivials);
		List<Individual> offspringMutationFitnessIndivials = controller.fitnessIndividuals(offspringMutationIndivials);
		List<Individual> aggregateGenerationFitnessIndivials = controller.fitnessIndividuals(aggregateGeneration);
		
//		print.printFitnessIndividuals(offspringCrossoverFitnessIndivials);
//		print.printFitnessIndividuals(offspringMutationFitnessIndivials);
		print.printFitnessIndividuals(aggregateGenerationFitnessIndivials);
	}
}
