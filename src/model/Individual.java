package model;

import java.util.List;

public class Individual {
	private List<Gene> genes; 
	private double fitness;
	public Individual(List<Gene> genes, double fitness) {
		super();
		this.genes = genes;
		this.fitness = fitness;
	}
	public Individual() {
		super();
	}
	public List<Gene> getGenes() {
		return genes;
	}
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
}
