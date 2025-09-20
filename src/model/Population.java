package model;

import java.util.List;

public class Population {
    private List<Chromosome> chromosomes;

	public Population(List<Chromosome> chromosomes) {
		super();
		this.chromosomes = chromosomes;
	}

	public List<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(List<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}
    
    
}

