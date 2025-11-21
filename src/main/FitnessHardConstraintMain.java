package main;

import controller.ScheduleController;
import model.Individual;
import model.Population;
import print.SchedulePrint;
import service.InitPopulationService;

public class FitnessHardConstraintMain {
	public static void main(String[] args) {
//		InitPopulationService initPopulationService = new InitPopulationService();
//		ScheduleController controller = new ScheduleController();
//		SchedulePrint print = new SchedulePrint();
//		Individual individual = initPopulationService.createIndividualPublic();
//		double fitness = controller.fitness(individual);
//		individual.setFitness(fitness);
//		print.printIndividual(individual);
		
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		Population population = controller.getPopulation();
		Population populationPrint = controller.fitnessIndividuals(population);
		print.printInitPopulation(populationPrint);
	}
}
