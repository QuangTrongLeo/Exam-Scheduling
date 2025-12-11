package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
    private List<TimeSlot> timeSlots; // Giữ lại để tham khảo nếu cần
    private Random random = new Random();
    
    // Tỷ lệ mutation vẫn giữ, nhưng logic bên trong sẽ quét toàn bộ lỗi
    public static final double MUTATION_RATE = 0.2;

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

    public Individual mutate(Individual original) {
        if (original == null) return null;
        Individual clone = original.cloneIndividual();
        
        // Gọi hàm sửa lỗi xung đột phòng
        resolveRoomConflicts(clone);
        
        return clone;
    }
    
    // --- LOGIC MỚI: TÌM VÀ SỬA XUNG ĐỘT PHÒNG ---
    private void resolveRoomConflicts(Individual individual) {
        // 1. Bản đồ đánh dấu: Key = "Day_Period_RoomID", Value = Danh sách các Session trùng nhau
        Map<String, List<ClassSession>> conflictMap = new HashMap<>();

        // Duyệt qua tất cả Gene và Session để gom nhóm
        for (Gene gene : individual.getGenes()) {
            for (ClassSession session : gene.getClassSessions()) {
                String key = generateKey(session.getTimeSlot(), session.getRoom());
                
                if (!conflictMap.containsKey(key)) {
                    conflictMap.put(key, new ArrayList<>());
                }
                conflictMap.get(key).add(session);
            }
        }

        // 2. Xử lý các nhóm có xung đột (Size > 1)
        for (List<ClassSession> conflictGroup : conflictMap.values()) {
            if (conflictGroup.size() > 1) {
                // Logic: Giữ lại phần tử đầu tiên (index 0) làm gốc.
                // Các phần tử từ index 1 trở đi sẽ bị đổi phòng.
                for (int i = 1; i < conflictGroup.size(); i++) {
                    ClassSession sessionToMove = conflictGroup.get(i);
                    moveSessionToEmptyRoom(sessionToMove, individual);
                }
            }
        }
    }

    // Hàm thực hiện việc đổi phòng cho session bị trùng
    private void moveSessionToEmptyRoom(ClassSession session, Individual individual) {
        TimeSlot currentSlot = session.getTimeSlot();
        boolean isLab = session.getRoom().isLab();
        List<Room> targetRooms = isLab ? labRooms : theoryRooms;

        // B1: Tìm danh sách các phòng ĐANG BẬN tại khung giờ này trong toàn bộ cá thể
        Set<Integer> occupiedRoomIds = getOccupiedRoomIdsAtTime(individual, currentSlot);

        // B2: Tìm danh sách phòng TRỐNG (Candidate rooms)
        List<Room> availableRooms = new ArrayList<>();
        for (Room r : targetRooms) {
            if (!occupiedRoomIds.contains(r.getId())) {
                availableRooms.add(r);
            }
        }

        // B3: Chọn phòng mới
        if (!availableRooms.isEmpty()) {
            // Tốt nhất: Chọn ngẫu nhiên một phòng trong số các phòng trống
            Room newRoom = availableRooms.get(random.nextInt(availableRooms.size()));
            session.setRoom(newRoom);
        } else {
            // Đường cùng (Full phòng): Chọn đại một phòng khác phòng hiện tại (Random mù)
            // Hy vọng lần sau may mắn hoặc cá thể này sẽ bị loại bỏ bởi Fitness
            Room currentRoom = session.getRoom();
            Room randomRoom = currentRoom;
            int attempts = 0;
            while (randomRoom.getId() == currentRoom.getId() && attempts < 10) {
                randomRoom = targetRooms.get(random.nextInt(targetRooms.size()));
                attempts++;
            }
            session.setRoom(randomRoom);
        }
    }

    // Helper: Lấy ID các phòng đang bận tại một khung giờ cụ thể
    private Set<Integer> getOccupiedRoomIdsAtTime(Individual individual, TimeSlot slot) {
        Set<Integer> occupiedParams = new HashSet<>();
        for (Gene gene : individual.getGenes()) {
            for (ClassSession session : gene.getClassSessions()) {
                // So sánh TimeSlot. Lưu ý: Dùng equals() cho TimeSlot
                if (session.getTimeSlot().equals(slot)) {
                    occupiedParams.add(session.getRoom().getId());
                }
            }
        }
        return occupiedParams;
    }

    // Helper: Tạo Key duy nhất cho Map
    private String generateKey(TimeSlot slot, Room room) {
        // Key dạng: "Day2_Period1_Room101"
        return "Day" + slot.getDay() + "_Period" + slot.getPeriod() + "_Room" + room.getId();
    }
}