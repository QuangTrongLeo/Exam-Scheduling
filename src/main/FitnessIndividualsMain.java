package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import model.Population;
import print.SchedulePrint;

public class FitnessIndividualsMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		Population population = controller.getPopulation();
		List<Individual> individuals = controller.fitnessIndividuals(population.getIndividuals());
		print.printFitnessIndividuals(individuals);
	}
}
