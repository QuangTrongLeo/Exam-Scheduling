package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Individual;

public class GenerationAggregationService {
	private final Random random = new Random();
	private final CrossoverService crossoverService;
	
	public GenerationAggregationService() {
		this.crossoverService = new CrossoverService();
	}
	
	public List<Individual> aggregateGeneration(List<Individual> individuals){
		List<Individual> aggregateIndividuals = new ArrayList<>();
		List<Individual> offspringCrossoverIndivials = offspringCrossoverIndivials(individuals);
		List<Individual> offspringMutationIndivials = offspringMutationIndivials(individuals);
		aggregateIndividuals.addAll(offspringCrossoverIndivials);
		aggregateIndividuals.addAll(offspringMutationIndivials);
		return aggregateIndividuals;
	}
    
	//	Danh sách cá thể khi lai tạo
    public List<Individual> offspringCrossoverIndivials(List<Individual> individuals) {
        List<Individual> remainingParents = new ArrayList<>(individuals);
        List<Individual> offspring = new ArrayList<>();
        int numPairs = individuals.size() / 2;
        
        for (int i = 0; i < numPairs; i++) {
            // Chọn và loại bỏ P1
            Individual p1 = selectParent(remainingParents); 
            // Chọn và loại bỏ P2
            Individual p2 = selectParent(remainingParents); 
            
            // Xử lý trường hợp không đủ cặp (nếu kích thước quần thể là số lẻ)
            if (p1 == null || p2 == null) {
                // Thêm cá thể cuối cùng còn sót lại vào offspring
                if (p1 != null) offspring.add(p1);
                break;
            }

            List<Individual> children = crossoverService.crossover(p1, p2);
            offspring.add(children.get(0));
            offspring.add(children.get(1));
        }
        return offspring;
    }
    
    // Danh sách cá thể khi đột biến
    public List<Individual> offspringMutationIndivials(List<Individual> individuals) {
    	List<Individual> offspring = new ArrayList<>();
    	return offspring;
    }

    private Individual selectParent(List<Individual> individuals) {
        int randomIndex = random.nextInt(individuals.size());
        Individual parent = individuals.get(randomIndex);
        individuals.remove(randomIndex); 
        return parent;
    }
}
