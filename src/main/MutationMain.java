package main;

import java.util.List;

import controller.ScheduleController;
import model.Individual;
import model.Population;
import print.SchedulePrint;

public class MutationMain {
    public static void main(String[] args) {
        ScheduleController controller = new ScheduleController();
        SchedulePrint printer = new SchedulePrint();
        
        // Khởi tạo quần thể ban đầu
        Population population = controller.initPopulation();
        
        // Tính fitness cho quần thể
        List<Individual> individuals = controller.fitnessIndividuals(population.getIndividuals());
        
        // Thực hiện mutation trên 10% cá thể
        List<Individual> mutatedIndividuals = controller.generateMutation(individuals);
        
        // In kết quả mutation
        System.out.println("Kết quả Mutation (10% cá thể) :");
        printer.printFitnessIndividuals(mutatedIndividuals);
    }
}