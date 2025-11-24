package main;

import controller.ScheduleController;
import model.Population;
import print.SchedulePrint;

public class FitnessIndividualsMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		Population population = controller.getPopulation();
		Population populationPrint = controller.fitnessIndividuals(population);
		print.printFitnessIndividuals(populationPrint);
	}
}
