package main;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;

public class TheBestIndividual {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		Individual theBestIndividual = controller.theBestIndividual();
		print.printIndividual(theBestIndividual);
	}
}
