package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;

public class CrossoverMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		List<Individual> individuals = controller.getAccumulatedIndividuals();
		List<Individual> crossoverIndividuals = controller.generateCrossover(individuals);
		List<Individual> fitnessIndividuals = controller.fitnessIndividuals(crossoverIndividuals);
		print.printFitnessIndividuals(fitnessIndividuals);
	}
}
