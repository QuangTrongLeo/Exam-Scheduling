package model;

import java.util.List;

public class Chromosome {
    private List<Gene> genes; // hoặc List<Gene>
    private double fitness;           // điểm fitness
    
	public Chromosome(List<Gene> genes, double fitness) {
		super();
		this.genes = genes;
		this.fitness = fitness;
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
	@Override
	public String toString() {
		return "Chromosome:\n genes = " + genes + "\nfitness = " + fitness;
	}
    
    
}
