package print;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Population;

public class SchedulePrint {
	public SchedulePrint() {}
	
	public void printPopulation(Population population) {
        int idx = 1;
        for (Individual individual : population.getIndividuals()) {
            System.out.println("----- Individual " + idx + " -----");
            printIndividual(individual);
            idx++;
        }
        System.out.println("========== Quần thể gồm " + population.getIndividuals().size() + " cá thể ==========\n");
    }

    
    public void printIndividual(Individual individual) {
        for (Gene gene : individual.getGenes()) {
            printGene(gene);
        }
        System.out.println("--------------------------------------------------\n");
    }

    
    public void printGene(Gene gene) {
	    System.out.println("Giảng viên: " + gene.getLecturer().getName());
	
	    Map<Integer, Map<Integer, ClassSession>> schedule = new TreeMap<>();
	    for (ClassSession cs : gene.getClassSessions()) {
	        int day = cs.getTimeSlot().getDay();
	        int period = cs.getTimeSlot().getPeriod();
	        schedule.putIfAbsent(day, new TreeMap<>()); // TreeMap để thứ tự ca tự nhiên
	        schedule.get(day).put(period, cs);
	    }
	
	    // Giả sử các ngày là 2 -> 7 và ca 1 -> 4
	    for (int day = 2; day <= 7; day++) {
	        System.out.print("   Thứ " + day + ": ");
	        List<String> periodStrings = new ArrayList<>();
	
	        for (int period = 1; period <= 4; period++) {
	            if (schedule.containsKey(day) && schedule.get(day).containsKey(period)) {
	                ClassSession cs = schedule.get(day).get(period);
	                periodStrings.add("Ca " + period + " - " 
	                                  + cs.getSubject().getName() + "(" + cs.getRoom().getName() + ")");
	            } else {
	                periodStrings.add("Ca " + period + " - trống");
	            }
	        }
	
	        System.out.println(String.join(" ; ", periodStrings));
	    }
	    System.out.println();
	}
}
