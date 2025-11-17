package model;

import java.util.ArrayList;
import java.util.List;

public class Population {
	private List<Individual> individuals;

	public Population() {
		 this.individuals = new ArrayList<>();
	}

	public Population(List<Individual> individuals) {
		super();
		this.individuals = individuals;
	}

	public List<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
	
	// Thêm 1 cá thể
	public void addIndividual(Individual individual) {
        this.individuals.add(individual);
    }
	
	// Thêm nhiều cá thể
	public void addIndividuals(List<Individual> newIndividuals) {
        this.individuals.addAll(newIndividuals);
    }
	
	// Số lượng cá thể trong quần thể
	public int sizeIndividuals() {
        return individuals.size();
    }
}
