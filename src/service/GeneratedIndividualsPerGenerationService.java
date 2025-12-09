package service;

import java.util.ArrayList;
import java.util.List;

import controller.ScheduleController;
import model.Individual;

public class GeneratedIndividualsPerGenerationService {
	
	private CrossoverService crossoverService;
    private MutationService mutationService;

    // Inject 2 service cần thiết vào
    public GeneratedIndividualsPerGenerationService(CrossoverService crossoverService, MutationService mutationService) {
        this.crossoverService = crossoverService;
        this.mutationService = mutationService;
    }
    
    public List<Individual> getGeneratedIndividualsPerGeneration(List<Individual> parents) {
        List<Individual> nextGeneration = new ArrayList<>();

        // 1. Tạo một bản sao danh sách cha mẹ để Crossover không làm hỏng list gốc (nếu cần dùng lại)
        // Tuy nhiên, CrossoverService hiện tại cắt list cha mẹ để ghép cặp.
        // Ta sẽ copy ra một list tạm để đưa vào Crossover.
        List<Individual> parentsPool = new ArrayList<>(parents);

        // 2. Thực hiện Lai ghép (Crossover)
        // Kết quả trả về là danh sách các con (Offspring)
        List<Individual> offspring = crossoverService.offspringCrossoverIndivials(parentsPool);
        
        // 3. Thực hiện Đột biến (Mutation) trên chính các con vừa sinh ra
        // (Theo thuyết tiến hóa: Con sinh ra có thể bị đột biến)
        List<Individual> mutatedOffspring = mutationService.mutation(offspring);

        // 4. Thêm vào thế hệ tiếp theo
        nextGeneration.addAll(mutatedOffspring);

        // Lưu ý: Nếu số lượng con sinh ra ít hơn quần thể ban đầu (do số lẻ hoặc logic select),
        // bạn có thể cần cơ chế Elitism (giữ lại cá thể tốt nhất của cha mẹ) ở Controller.
        
        return nextGeneration;
    }
}
