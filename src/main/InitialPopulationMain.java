package main;

import controller.ScheduleController;
import service.InitialPopulationService;

public class InitialPopulationMain {
	public static void main(String[] args) {
		InitialPopulationService service = InitialPopulationService.getInstance();
		ScheduleController controller = new ScheduleController(service);
		controller.initialPopulation();
    }
}
