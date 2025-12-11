package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Individual;

public class GenerationAggregationService {
	private final Random random = new Random();
	private final CrossoverService crossoverService;
	private final MutationService mutationService;
	
	public GenerationAggregationService() {
		this.crossoverService = new CrossoverService();
		this.mutationService = new MutationService();
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

            List<Individual> children = crossoverService.crossover(p1, p2);
            offspring.add(children.get(0));
            offspring.add(children.get(1));
        }
        
        // Trường hợp nếu không đủ cặp
        if (!remainingParents.isEmpty()) {
            offspring.add(remainingParents.get(0));
        }
        return offspring;
    }
    
    // Danh sách cá thể khi đột biến
    public List<Individual> offspringMutationIndivials(List<Individual> individuals) {
        List<Individual> offspring = new ArrayList<>();
        
        // Tính toán số lượng cụ thể cần đột biến (ví dụ: 100 * 0.1 = 10)
        int numberOfMutations = (int) (individuals.size() * MutationService.MUTATION_RATE);
        
        // Tạo danh sách index để không chọn trùng (nếu cần) hoặc cứ random thoải mái
        for (int i = 0; i < numberOfMutations; i++) {
            // Chọn ngẫu nhiên 1 index trong danh sách
            int randomIndex = random.nextInt(individuals.size());
            Individual original = individuals.get(randomIndex);
            
            // Mutate
            Individual mutatedInd = mutationService.mutate(original);
            if (mutatedInd != null) {
                offspring.add(mutatedInd);
            }
        }
        
        return offspring;
    }

    private Individual selectParent(List<Individual> individuals) {
        int randomIndex = random.nextInt(individuals.size());
        Individual parent = individuals.get(randomIndex);
        individuals.remove(randomIndex); 
        return parent;
    }
}
