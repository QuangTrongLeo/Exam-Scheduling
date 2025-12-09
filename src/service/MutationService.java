package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import data.LecturerData;
import data.RoomData;
import data.TimeSlotData;
import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Lecturer;
import model.Room;
import model.Subject;
import model.TimeSlot;

public class MutationService {
    private List<Room> rooms;
    private List<TimeSlot> timeSlots;
    private Random random = new Random();
    
    // Tỷ lệ mutation: 10% số lượng cá thể
    private static final double MUTATION_RATE = 0.1;

    public MutationService() {
        this.rooms = RoomData.initRooms();
        this.timeSlots = TimeSlotData.initTimeSlots();
    }

    // Phương thức chính: Mutation trên list individuals
    public List<Individual> mutation(List<Individual> individuals) {
        int numToMutate = (int) (individuals.size() * MUTATION_RATE);
        List<Individual> mutated = new ArrayList<>();
        
        // Chọn ngẫu nhiên numToMutate cá thể để mutation
        List<Individual> candidates = new ArrayList<>(individuals);
        for (int i = 0; i < numToMutate; i++) {
            if (candidates.isEmpty()) break;
            int idx = random.nextInt(candidates.size());
            Individual original = candidates.get(idx);
            candidates.remove(idx);
            
            // Clone và mutate
            Individual clone = original.cloneIndividual();
            mutateIndividual(clone);
            mutated.add(clone);
        }
        
        return mutated;
    }
    
    // Mutation một individual: Random chọn 1 gene, rồi random 1-2 ClassSession trong gene để thay đổi room/timeslot
    private void mutateIndividual(Individual individual) {
        List<Gene> genes = individual.getGenes();
        if (genes.isEmpty()) return;
        
        // Chọn ngẫu nhiên 1 gene để mutation
        Gene gene = genes.get(random.nextInt(genes.size()));
        List<ClassSession> sessions = gene.getClassSessions();
        if (sessions.isEmpty()) return;
        
        // Chọn ngẫu nhiên 1-2 session để mutate (để tăng độ biến đổi nhưng không quá nhiều)
        int numSessionsToMutate = random.nextInt(2) + 1; // 1 hoặc 2
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < numSessionsToMutate && selectedIndices.size() < sessions.size()) {
            selectedIndices.add(random.nextInt(sessions.size()));
        }
        
        for (int idx : selectedIndices) {
            ClassSession session = sessions.get(idx);
            mutateClassSession(session, gene);
        }
    }
    
    // Mutation một ClassSession: Random thay đổi room, timeslot, hoặc cả hai
    private void mutateClassSession(ClassSession session, Gene gene) {
        boolean changeRoom = random.nextBoolean(); // 50% thay đổi room
        boolean changeTimeslot = random.nextBoolean(); // 50% thay đổi timeslot
        
        if (!changeRoom && !changeTimeslot) {
            // Nếu không chọn gì, buộc thay đổi ít nhất một cái
            if (random.nextBoolean()) changeRoom = true;
            else changeTimeslot = true;
        }
        
        if (changeRoom) {
            Room newRoom = pickSuitableRoom(session.getSubject());
            session.setRoom(newRoom);
        }
        
        if (changeTimeslot) {
            TimeSlot newSlot = pickAvailableTimeSlot(gene, session.getTimeSlot());
            session.setTimeSlot(newSlot);
        }
    }
    
    // Chọn phòng phù hợp (tương tự InitPopulationService)
    private Room pickSuitableRoom(Subject subject) {
        List<Room> suitableRooms;
        if (subject.getLabLessons() > 0) {
            suitableRooms = filterLabRooms(rooms);
        } else {
            suitableRooms = filterTheoryRooms(rooms);
            // Ưu tiên capacity cho môn core
            List<Room> filtered = new ArrayList<>();
            if (subject.isCore()) {
                for (Room r : suitableRooms) if (r.getCapacity() == 120) filtered.add(r);
            } else {
                for (Room r : suitableRooms) if (r.getCapacity() == 80 || r.getCapacity() == 120) filtered.add(r);
            }
            suitableRooms = filtered.isEmpty() ? suitableRooms : filtered;
        }
        return suitableRooms.get(random.nextInt(suitableRooms.size()));
    }
    
    // Chọn timeslot mới không conflict trong gene (không trùng với các session khác)
    private TimeSlot pickAvailableTimeSlot(Gene gene, TimeSlot current) {
        Set<TimeSlot> usedSlots = new HashSet<>();
        for (ClassSession cs : gene.getClassSessions()) {
            if (!cs.getTimeSlot().equals(current)) { // Bỏ qua slot hiện tại
                usedSlots.add(cs.getTimeSlot());
            }
        }
        
        List<TimeSlot> available = new ArrayList<>();
        for (TimeSlot ts : timeSlots) {
            if (!usedSlots.contains(ts)) {
                available.add(ts);
            }
        }
        
        if (available.isEmpty()) {
            // Nếu hết, trả về random (có thể gây conflict, fitness sẽ xử lý)
            return timeSlots.get(random.nextInt(timeSlots.size()));
        }
        
        return available.get(random.nextInt(available.size()));
    }
    
    // Helper: Lọc phòng lý thuyết
    private List<Room> filterTheoryRooms(List<Room> rooms) {
        List<Room> list = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isLab()) list.add(room);
        }
        return list;
    }

    // Helper: Lọc phòng thực hành
    private List<Room> filterLabRooms(List<Room> rooms) {
        List<Room> list = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isLab()) list.add(room);
        }
        return list;
    }
}