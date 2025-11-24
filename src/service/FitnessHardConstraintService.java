package service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Room;
import model.TimeSlot;

public class FitnessHardConstraintService {
	public boolean checkHardConstraint(Individual individual) {
		return hasConflictInGene(individual) || hasConflictBetweenGenes(individual);
	}
	
	private boolean hasConflictInGene(Individual individual) {
        List<Gene> genes = individual.getGenes();

        for (Gene gene : genes) {
            Set<TimeSlot> occupiedSlots = new HashSet<>();
            for (ClassSession cs : gene.getClassSessions()) {
                TimeSlot ts = cs.getTimeSlot();
                if (occupiedSlots.contains(ts)) {
                    return true;
                }
                occupiedSlots.add(ts);
            }
        }

        return false;
    }

    private boolean hasConflictBetweenGenes(Individual individual) {
        Map<Room, Set<TimeSlot>> roomSchedule = new HashMap<>();

        for (Gene gene : individual.getGenes()) {
            for (ClassSession cs : gene.getClassSessions()) {
                Room room = cs.getRoom();
                TimeSlot slot = cs.getTimeSlot();
                roomSchedule.putIfAbsent(room, new HashSet<>());
                
                if (roomSchedule.get(room).contains(slot)) {
                    return true;
                }

                roomSchedule.get(room).add(slot);
            }
        }

        return false;
    }

}
