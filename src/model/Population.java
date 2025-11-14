package model;

import java.util.List;

public class Population {
	private List<Individual> individuals;

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
	
	
}
