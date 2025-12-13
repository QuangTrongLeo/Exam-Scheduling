package service;

import java.util.ArrayList;
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
		return hasTimeSlotConflictInGen(individual) || hasRoomConflictBetweenGens(individual) || hasCoreTimeSlotConflictBetweenGens(individual);
	}
	
	private boolean hasTimeSlotConflictInGen(Individual individual) {
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

    private boolean hasRoomConflictBetweenGens(Individual individual) {
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
    
    private boolean hasCoreTimeSlotConflictBetweenGens(Individual individual) {
        List<ClassSession> coreSessions = new ArrayList<>();
        for (Gene gene : individual.getGenes()) {
            for (ClassSession session : gene.getClassSessions()) {
                if (session.getSubject().isCore()) {
                    coreSessions.add(session);
                }
            }
        }
        for (int i = 0; i < coreSessions.size(); i++) {
            for (int j = i + 1; j < coreSessions.size(); j++) {
                ClassSession cs1 = coreSessions.get(i);
                ClassSession cs2 = coreSessions.get(j);
                if (cs1.getTimeSlot().equals(cs2.getTimeSlot())) {
                    return true; 
                }
            }
        }
        return false;
    }

}
