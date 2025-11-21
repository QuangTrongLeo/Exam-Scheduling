// File: main/FitnessTestMain.java
package main;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;
import service.InitPopulationService;

public class FitnessTestMain {
    public static void main(String[] args) {
    	ScheduleController controller = new ScheduleController();
    	SchedulePrint printer = new SchedulePrint();
    	
    	// Tạo 1 cá thể để test
        Individual individual = new InitPopulationService().createIndividual();
    	
    	
    	double fitness = controller.fitness(individual);        
        printer.printIndividual(individual);
        System.out.println("Fitness của cá thể này: " + fitness);
        
    }
}