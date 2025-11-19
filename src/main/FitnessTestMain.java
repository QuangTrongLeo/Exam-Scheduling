// File: main/FitnessTestMain.java
package main;

import model.Individual;
import print.SchedulePrint;
import service.FitnessService;
import service.InitPopulationService;

public class FitnessTestMain {
    public static void main(String[] args) {
        System.out.println("===== BẮT ĐẦU TEST FITNESS (Soft Constraints) =====");

        // Bước 1: Tạo 1 cá thể ngẫu nhiên
        InitPopulationService initService = new InitPopulationService();
        Individual individual = initService.createIndividualPublic(); // Dùng method public mới

        // Bước 2: Tính fitness từ các ràng buộc mềm
        FitnessService fitnessService = new FitnessService();
        double fitness = fitnessService.calculateFitness(individual);
        individual.setFitness(fitness);

        // Bước 3: In kết quả
        SchedulePrint printer = new SchedulePrint();
        printer.printIndividual(individual);

        System.out.println("===== HOÀN TẤT TEST FITNESS =====");
        System.out.println("Fitness của cá thể này: " + String.format("%.2f", fitness));
    }
}