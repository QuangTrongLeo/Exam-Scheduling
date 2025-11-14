package main;

import controller.ScheduleController;
import print.SchedulePrint;

public class InitPopulationMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		SchedulePrint print = new SchedulePrint();
		print.printPopulation(controller.initPopulation());
	}
}
