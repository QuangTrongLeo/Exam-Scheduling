package service;

import java.util.*;

import data.LecturerData;
import data.RoomData;
import data.SubjectClassCountData;
import data.SubjectData;
import data.TimeSlotData;
import model.*;

public class InitialPopulationService {
    private static InitialPopulationService instance;

    private final List<Lecturer> lecturers;
    private final List<Room> rooms;
    private final List<TimeSlot> timeSlots;
    private final List<Subject> subjects;
    private final List<SubjectClassCount> subjectClassCounts;

    public InitialPopulationService() {
        this.lecturers = LecturerData.initLectutes();
        this.rooms = RoomData.initRooms();
        this.timeSlots = TimeSlotData.initTimeSlots();
        this.subjects = SubjectData.initSubjects();
        this.subjectClassCounts = SubjectClassCountData.initClassCounts(subjects);
    }

    public static InitialPopulationService getInstance() {
        if (instance == null) {
            instance = new InitialPopulationService();
        }
        return instance;
    }

    // === Tạo quần thể ban đầu với n cá thể ===
    public List<Chromosome> createInitialPopulation(int chromosomeSize) {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosomes.add(createChromosome());
        }
        return chromosomes;
    }

    // === Tạo Chromosome (cho tất cả giảng viên) ===
    public Chromosome createChromosome() {
        List<Gene> genes = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            genes.add(createGen(lecturer));
        }
        return new Chromosome(genes, 0);
    }

    // === Tạo Gene (lịch dạy) cho 1 giảng viên ===
    public Gene createGen(Lecturer lecturer) {
        List<ClassSession> lessons = new ArrayList<>();
        Set<TimeSlot> usedTimeSlots = new HashSet<>();
        Random r = new Random();

        // Phân loại phòng
        List<Room> labRooms = new ArrayList<>();
        List<Room> theoryRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isLab()) labRooms.add(room);
            else theoryRooms.add(room);
        }

        // Duyệt qua từng môn mà giảng viên dạy
        for (Subject subject : lecturer.getSubjects()) {
            SubjectClassCount count = getClassCountForSubject(subject);
            if (count == null) continue; // bỏ qua nếu không tìm thấy dữ liệu

            int theory = count.getTheory();
            int lab = count.getLab();

            // Tạo ca lý thuyết
            createLessons(lessons, subject, theory, theoryRooms, usedTimeSlots, timeSlots, r);

            // Tạo ca thực hành
            createLessons(lessons, subject, lab, labRooms, usedTimeSlots, timeSlots, r);
        }

        return new Gene(lecturer, lessons);
    }

    // === Lấy số ca LT/TH của một môn học ===
    private SubjectClassCount getClassCountForSubject(Subject subject) {
        for (SubjectClassCount scc : subjectClassCounts) {
            if (scc.getSubject().getName().equals(subject.getName())) {
                return scc;
            }
        }
        return null;
    }

    // === Sinh tiết học (random khung giờ và phòng học) ===
    private void createLessons(List<ClassSession> lessons, Subject subject, int count,
                               List<Room> roomList, Set<TimeSlot> usedTimeSlots,
                               List<TimeSlot> timeSlots, Random r) {
        for (int i = 0; i < count; i++) {
            TimeSlot ts;
            do {
                ts = timeSlots.get(r.nextInt(timeSlots.size()));
            } while (usedTimeSlots.contains(ts)); // tránh trùng ca
            usedTimeSlots.add(ts);

            Room room = roomList.get(r.nextInt(roomList.size()));
            lessons.add(new ClassSession(subject, room, ts));
        }
    }

    // === In thông tin lịch dạy của 1 giảng viên ===
    public void printGene(Gene gene) {
        Lecturer lecturer = gene.getLecturer();
        System.out.println("Giảng viên: " + lecturer.getName());

        // In danh sách môn học và số ca LT/TH
        List<String> subjectInfos = new ArrayList<>();
        for (Subject subject : lecturer.getSubjects()) {
            SubjectClassCount count = getClassCountForSubject(subject);
            if (count != null) {
                String info = subject.getName() + " (" + count.getTheory() + " LT, " + count.getLab() + " TH)";
                subjectInfos.add(info);
            }
        }
        System.out.println("Môn học: " + String.join(", ", subjectInfos));

        // Lịch dạy theo thứ/ca
        Map<Integer, Map<Integer, ClassSession>> scheduleMap = new HashMap<>();
        for (int day = 2; day <= 7; day++) {
            scheduleMap.put(day, new HashMap<>());
        }

        for (ClassSession lesson : gene.getSessions()) {
            int day = lesson.getTimeSlot().getDay();
            int period = lesson.getTimeSlot().getPeriod();
            scheduleMap.get(day).put(period, lesson);
        }

        for (int day = 2; day <= 7; day++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thứ ").append(day).append(": ");
            for (int period = 1; period <= 4; period++) {
                ClassSession lesson = scheduleMap.get(day).get(period);
                if (lesson != null) {
                    sb.append("Ca").append(period)
                            .append(" - ").append(lesson.getSubject().getName())
                            .append(" (").append(lesson.getRoom().getName()).append("), ");
                } else {
                    sb.append("Ca").append(period).append(" - trống, ");
                }
            }
            if (sb.length() > 2) sb.setLength(sb.length() - 2);
            System.out.println(sb);
        }
        System.out.println();
    }
}
