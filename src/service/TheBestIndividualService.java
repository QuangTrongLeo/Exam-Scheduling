package service;

import java.util.List;

import model.Individual;

public class TheBestIndividualService {
	private final AccumulateIndividualsService accumulateIndividualsService;
	
	public TheBestIndividualService() {
		this.accumulateIndividualsService = new AccumulateIndividualsService();
	}
	
	public Individual theBestIndividual() {
        List<Individual> individuals = accumulateIndividualsService.accumulateIndividuals();
        Individual theBestIndividual = individuals.get(0);
        int bestIndex = 0;

        for (int i = 1; i < individuals.size(); i++) {
            Individual currentIndividual = individuals.get(i);
            if (currentIndividual.getFitness() > theBestIndividual.getFitness()) {
            	theBestIndividual = currentIndividual;
                bestIndex = i;
            }
        }

        System.out.println("----- Cá thể " + bestIndex + " là cá thể tốt nhất -----");
        System.out.println("----- Cá thể này có fitness: " + theBestIndividual.getFitness() + " -----");
        return theBestIndividual;
    }
}
