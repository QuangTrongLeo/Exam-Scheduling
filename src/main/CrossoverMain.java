package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;
import service.CrossoverService;
import service.InitPopulationService;

public class CrossoverMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		InitPopulationService service = new InitPopulationService();
		SchedulePrint print = new SchedulePrint();
//		List<Individual> individuals = controller.getAccumulatedIndividuals();
//		List<Individual> crossoverIndividuals = controller.generateCrossover(individuals);
//		List<Individual> fitnessIndividuals = controller.fitnessIndividuals(crossoverIndividuals);
//		print.printFitnessIndividuals(fitnessIndividuals);
		Individual p1 = service.createIndividual();
		Individual p2 = service.createIndividual();
		print.printIndividual(p1);
		print.printIndividual(p2);
		System.out.println("----- LAI TẠO -----");
		List<Individual> individuals = controller.crossover(p1, p2);
		for (Individual individual : individuals) {
			print.printIndividual(individual);
		}
	}
}
