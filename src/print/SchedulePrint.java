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
	
	public void printInitPopulation(Population population) {
        int idx = 1;
        for (Individual individual : population.getIndividuals()) {
            System.out.println("----- Individual " + idx + " -----");
            printIndividual(individual);
            idx++;
        }
        System.out.println("========== Quần thể gồm " + population.sizeIndividuals() + " cá thể ==========\n");
    }

    
	public void printIndividual(Individual individual) {
	    System.out.println("=== FITNESS: " + String.format("%.2f", individual.getFitness()) + " ===");
	    for (Gene gene : individual.getGenes()) {
	        printGene(gene);
	    }
	    System.out.println("--------------------------------------------------\n");
	}

    
    public void printGene(Gene gene) {
        System.out.println("Giảng viên: " + gene.getLecturer().getName());

        Map<Integer, Map<Integer, List<ClassSession>>> schedule = new TreeMap<>();
        for (ClassSession cs : gene.getClassSessions()) {
            int day = cs.getTimeSlot().getDay();
            int period = cs.getTimeSlot().getPeriod();
            schedule.putIfAbsent(day, new TreeMap<>());
            schedule.get(day).putIfAbsent(period, new ArrayList<>());
            schedule.get(day).get(period).add(cs);
        }

        for (int day = 2; day <= 7; day++) {
            System.out.print("   Thứ " + day + ": ");
            List<String> periodStrings = new ArrayList<>();

            for (int period = 1; period <= 4; period++) {
                if (schedule.containsKey(day) && schedule.get(day).containsKey(period)) {
                    List<ClassSession> sessions = schedule.get(day).get(period);
                    List<String> sessionStrings = new ArrayList<>();
                    for (ClassSession cs : sessions) {
                        sessionStrings.add(cs.getSubject().getName() + "(" + cs.getRoom().getName() + ")");
                    }
                    periodStrings.add("Ca " + period + " - " + String.join(" - ", sessionStrings));
                } else {
                    periodStrings.add("Ca " + period + " - trống");
                }
            }

            System.out.println(String.join(" ; ", periodStrings));
        }
        System.out.println();
    }
}
