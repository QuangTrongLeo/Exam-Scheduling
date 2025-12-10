package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.RoomData;
import data.TimeSlotData;
import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Room;
import model.TimeSlot;

public class MutationService {
    private List<Room> theoryRooms;
    private List<Room> labRooms;
    
    private List<TimeSlot> timeSlots;
    private Random random = new Random();
    
    // Tỷ lệ mutation
    public static final double MUTATION_RATE = 0.1;

    public MutationService() {
        this.timeSlots = TimeSlotData.initTimeSlots();
        
        List<Room> allRooms = RoomData.initRooms();
        this.theoryRooms = new ArrayList<>();
        this.labRooms = new ArrayList<>();
        
        for (Room room : allRooms) {
            if (room.isLab()) {
                this.labRooms.add(room);
            } else {
                this.theoryRooms.add(room);
            }
        }
    }

    // Phương thức chính: Mutate một cá thể duy nhất
    public Individual mutate(Individual original) {
        if (original == null) {
            return null;
        }
        // Clone để không ảnh hưởng dữ liệu gốc
        Individual clone = original.cloneIndividual();
        mutateIndividual(clone);
        return clone;
    }
    
    // Logic mutate thực tế
    private void mutateIndividual(Individual individual) {
        // Chọn ngẫu nhiên 1 gene (Giảng viên)
        int geneIdx = random.nextInt(individual.getGenes().size());
        Gene gene = individual.getGenes().get(geneIdx);
        
        // Chọn ngẫu nhiên 1 session (Lớp học)
        int sessionIdx = random.nextInt(gene.getClassSessions().size());
        ClassSession session = gene.getClassSessions().get(sessionIdx);
        
        // Mutate: 50% đổi phòng, 50% đổi giờ
        if (random.nextBoolean()) {
            if (session.getRoom().isLab()) {
                session.setRoom(getRandomElement(labRooms));
            } else {
                session.setRoom(getRandomElement(theoryRooms));
            }
        } else {
            session.setTimeSlot(getRandomElement(timeSlots));
        }
    }
    
    // Hàm tiện ích để lấy phần tử ngẫu nhiên từ list
    private <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }
}