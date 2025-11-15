package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import data.LecturerData;
import data.RoomData;
import data.TimeSlotData;
import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Lecturer;
import model.Population;
import model.Room;
import model.Subject;
import model.TimeSlot;

public class InitPopulationService {

    private List<Lecturer> lecturers;
    private List<Room> rooms;
    private List<TimeSlot> timeSlots;
    private Random random = new Random();

    public InitPopulationService() {
        this.lecturers = LecturerData.initLectutes();
        this.rooms = RoomData.initRooms();
        this.timeSlots = TimeSlotData.initTimeSlots();
    }

    // Khởi tạo Population
    public Population initializePopulation(int size) {
        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            individuals.add(createIndividual());
        }
        return new Population(individuals);
    }

    // Tạo cá thể
    private Individual createIndividual() {
        Individual individual = new Individual();
        List<Gene> genes = new ArrayList<>();
        for (Lecturer l : lecturers) {
            genes.add(createGene(l));
        }
        individual.setGenes(genes);
        individual.setFitness(0);
        return individual;
    }

    // Tạo Gene (lịch dạy của 1 giảng viên)
    private Gene createGene(Lecturer lecturer) {
        Gene gene = new Gene();
        gene.setLecturer(lecturer);
        List<ClassSession> classSessions = new ArrayList<>();

        List<Room> theoryRooms = filterTheoryRooms(rooms);
        List<Room> labRooms = filterLabRooms(rooms);

        for (Subject subject : lecturer.getSubjects()) {
            // --- Ca lý thuyết ---
            ClassSession theorySession = new ClassSession();
            theorySession.setLecturer(lecturer);
            theorySession.setSubject(subject);

            Room theoryRoom = pickTheoryRoom(subject, theoryRooms, random);
            theorySession.setRoom(theoryRoom);
            theorySession.setTimeSlot(pickRandomTimeSlot(timeSlots, random));
            classSessions.add(theorySession);

            // --- Ca thực hành ---
            int labLessons = numLabSession(subject, theoryRoom);
            for (int i = 0; i < labLessons; i++) {
                ClassSession labSession = new ClassSession();
                labSession.setLecturer(lecturer);
                labSession.setSubject(subject);
                labSession.setRoom(pickRandomRoom(labRooms, random));
                labSession.setTimeSlot(pickRandomTimeSlot(timeSlots, random));
                classSessions.add(labSession);
            }
        }

        gene.setClassSessions(classSessions);
        return gene;
    }

    // Lọc phòng lý thuyết
    private List<Room> filterTheoryRooms(List<Room> rooms) {
        List<Room> list = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isLab()) list.add(room);
        }
        return list;
    }

    // Lọc phòng thực hành
    private List<Room> filterLabRooms(List<Room> rooms) {
        List<Room> list = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isLab()) list.add(room);
        }
        return list;
    }

    // Chọn phòng lý thuyết phù hợp
    private Room pickTheoryRoom(Subject subject, List<Room> theoryRooms, Random random) {
        List<Room> filtered = new ArrayList<>();
        if (subject.isCore()) {
            for (Room r : theoryRooms) if (r.getCapacity() == 120) filtered.add(r);
        } else {
            for (Room r : theoryRooms) if (r.getCapacity() == 80 || r.getCapacity() == 120) filtered.add(r);
        }
        return filtered.get(random.nextInt(filtered.size()));
    }

    // Chọn phòng thực hành ngẫu nhiên
    private Room pickRandomRoom(List<Room> rooms, Random random) {
        return rooms.get(random.nextInt(rooms.size()));
    }

    // Chọn ca học ngẫu nhiên
    private TimeSlot pickRandomTimeSlot(List<TimeSlot> slots, Random random) {
        return slots.get(random.nextInt(slots.size()));
    }
    
    // Số lượng ca thực hành
    private int numLabSession(Subject subject, Room room) {
    	int labLessons;
    	if (subject.getLabLessons() == 0) {
			labLessons = 0;
		}
    	else if (subject.isCore() || room.getCapacity() == 120) {
            labLessons = 3;
        } else {
            labLessons = 2;
        }
    	return labLessons;
    }
}
