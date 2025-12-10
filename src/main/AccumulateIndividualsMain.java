package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;

public class AccumulateIndividualsMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		List<Individual> individuals = controller.accumulateIndividuals();
		print.printFitnessIndividuals(individuals);
	}
}
