package main;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;
import service.InitPopulationService;

public class MutationMain {
	public static void main(String[] args) {
		ScheduleController controller = new ScheduleController();
		InitPopulationService service = new InitPopulationService();
		SchedulePrint print = new SchedulePrint();
		
		// Tạo 1 cá thể mẫu để test (Giống cách CrossoverMain tạo p1, p2)
		Individual individual = service.createIndividual();
		
		// In ra cá thể gốc
		System.out.println("TRƯỚC KHI ĐỘT BIẾN (BEFORE)");
		// Tính fitness để hiển thị cho đầy đủ thông tin
		double oldFitness = controller.fitness(individual);
		individual.setFitness(oldFitness);
		print.printIndividual(individual);
		System.out.println("Fitness gốc: " + oldFitness);
		
		// Thực hiện Đột biến thông qua Controller
		Individual mutatedIndividual = controller.mutation(individual);
		
		// In ra kết quả sau đột biến
		System.out.println("\nSAU KHI ĐỘT BIẾN (AFTER)");
		double newFitness = controller.fitness(mutatedIndividual);
		mutatedIndividual.setFitness(newFitness);
		
		print.printIndividual(mutatedIndividual);
		System.out.println("Fitness mới: " + newFitness);
	}
}