// File: service/FitnessService.java
package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Lecturer;
import model.Subject;

public class FitnessSoftConstraint {
	
    private static final double MAX_FITNESS = 20000;

    public FitnessSoftConstraint() {}

    public double calculateFitness(Individual individual) {
    	double totalPenalty = 0;

        for (Gene gene : individual.getGenes()) {
            Lecturer lecturer = gene.getLecturer();
            List<ClassSession> sessions = gene.getClassSessions();

            // Nhóm buổi học theo ngày (dùng Map thủ công)
            Map<Integer, List<ClassSession>> sessionsByDay = new HashMap<>();
            for (ClassSession cs : sessions) {
                int day = cs.getTimeSlot().getDay();
                if (!sessionsByDay.containsKey(day)) {
                    sessionsByDay.put(day, new ArrayList<>());
                }
                sessionsByDay.get(day).add(cs);
            }

            // S1: Giảm số ngày dạy → Ưu tiên dạy ít ngày trong tuần (mục tiêu 3-4 ngày)
            totalPenalty += penaltyTeachingDays(sessionsByDay);

            // S2: Giảm số ngày dạy cùng môn (gói gọn 1 môn trong ít ngày)
            totalPenalty += penaltySubjectSpread(lecturer, sessions);

            // S3: Môn cốt lõi ưu tiên dạy ca 1-2
            totalPenalty += penaltyCoreSubjectLateSlots(sessions);

            // S4: Không dạy quá 3 ca liên tiếp (tránh overload)
            totalPenalty += penaltyConsecutivePeriods(sessionsByDay);

            // S5: Giảm khoảng trống (idle gap) giữa các ca trong ngày
            totalPenalty += penaltyIdleGaps(sessionsByDay);

            // S7: Cân bằng số ca dạy mỗi ngày (tránh ngày quá tải, ngày quá nhẹ)
            totalPenalty += penaltyUnbalancedDailyLoad(sessionsByDay);

            // S11: Ưu tiên có ít nhất 1 ngày nghỉ hoàn toàn
            totalPenalty += penaltyNoFullRestDay(sessionsByDay);

            // S12: Tránh dạy ca 2 và ca 3 liên tiếp mà không có nghỉ trưa (ca 2: 8h30-10h,
            // ca 3: 10h15-11h45 → có nghỉ)
            // → Thực tế ca 2 và ca 3 CÓ nghỉ 15 phút → chấp nhận được, nên bỏ hoặc giảm nhẹ

            // S15: Lab không dạy ca 4 (học muộn mệt)
            totalPenalty += penaltyLabInPeriod4(sessions);
        }

        // Fitness = càng ít penalty càng tốt → fitness cao = tốt
        double fitness = MAX_FITNESS - totalPenalty;
        return Math.max(0, fitness); // không âm
    }
    
    //  Tính fitness và tự động gán vào Individual
    public double fitness(Individual individual) {
        double f = calculateFitness(individual);
        return f;
    }

    // S1 + S11: Ưu tiên dạy ít ngày (3-4 ngày), có ít nhất 1 ngày nghỉ hoàn toàn
    private double penaltyTeachingDays(Map<Integer, List<ClassSession>> sessionsByDay) {
        int teachingDays = sessionsByDay.size();
        double penalty = 0;
        if (teachingDays > 4)
            penalty += (teachingDays - 4) * 40; // dạy >4 ngày: phạt nặng
        if (teachingDays < 3)
            penalty += (3 - teachingDays) * 30; // dạy <3 ngày: cũng không tốt
        if (teachingDays == 6)
            penalty += 150; // dạy full tuần: phạt rất nặng
        if (sessionsByDay.size() < 6)
            penalty -= 30; // có ngày nghỉ: thưởng nhẹ
        return penalty;
    }

    // S2: Mỗi môn nên gói gọn trong ít ngày (tối đa 2 ngày)
    private double penaltySubjectSpread(Lecturer lecturer, List<ClassSession> sessions) {
        double penalty = 0;

        for (Subject subject : lecturer.getSubjects()) {
            Set<Integer> days = new HashSet<>();
            for (ClassSession cs : sessions) {
                if (cs.getSubject().getId() == subject.getId()) {
                    days.add(cs.getTimeSlot().getDay());
                }
            }
            if (days.size() > 2) {
                penalty += (days.size() - 2) * 35;
            }
        }
        return penalty;
    }

    // S3: Môn cốt lõi nên dạy ca 1-2
    private double penaltyCoreSubjectLateSlots(List<ClassSession> sessions) {
        double penalty = 0;
        for (ClassSession cs : sessions) {
            if (cs.getSubject().isCore() && cs.getTimeSlot().getPeriod() >= 3) {
                penalty += 50;
            }
        }
        return penalty;
    }

    // S4: Không dạy quá 3 ca liên tiếp trong 1 ngày
    private double penaltyConsecutivePeriods(Map<Integer, List<ClassSession>> sessionsByDay) {
        double penalty = 0.0;

        for (List<ClassSession> daySessions : sessionsByDay.values()) {
            Set<Integer> periods = new HashSet<>();
            for (ClassSession cs : daySessions) {
                periods.add(cs.getTimeSlot().getPeriod());
            }

            boolean hasFour = periods.size() == 4;
            if (hasFour) {
                penalty += 100;
            } else if (periods.size() >= 3) {
                // Kiểm tra 3 ca liên tiếp: 1-2-3 hoặc 2-3-4
                if (periods.contains(1) && periods.contains(2) && periods.contains(3)) penalty += 50;
                if (periods.contains(2) && periods.contains(3) && periods.contains(4)) penalty += 50;
            }
        }
        return penalty;
    }

    // S5: Giảm khoảng trống giữa các ca
    private double penaltyIdleGaps(Map<Integer, List<ClassSession>> sessionsByDay) {
        double penalty = 0;

        for (List<ClassSession> daySessions : sessionsByDay.values()) {
            Set<Integer> periods = new HashSet<>();
            for (ClassSession cs : daySessions) {
                periods.add(cs.getTimeSlot().getPeriod());
            }

            List<Integer> sorted = new ArrayList<>(periods);
            Collections.sort(sorted);

            for (int i = 0; i < sorted.size() - 1; i++) {
                int gap = sorted.get(i + 1) - sorted.get(i);
                if (gap > 1) {
                    penalty += (gap - 1) * 20;
                }
            }
        }
        return penalty;
    }

    // S7: Cân bằng số ca dạy mỗi ngày
    private double penaltyUnbalancedDailyLoad(Map<Integer, List<ClassSession>> sessionsByDay) {
        if (sessionsByDay.isEmpty()) return 0;

        List<Integer> dailyCounts = new ArrayList<>();
        for (List<ClassSession> day : sessionsByDay.values()) {
            Set<Integer> periods = new HashSet<>();
            for (ClassSession cs : day) {
                periods.add(cs.getTimeSlot().getPeriod());
            }
            dailyCounts.add(periods.size());
        }

        double sum = 0;
        for (int c : dailyCounts) sum += c;
        double avg = sum / dailyCounts.size();

        double penalty = 0;
        for (int c : dailyCounts) {
            penalty += Math.abs(c - avg) * 15;
        }
        return penalty;
    }

    // S11: Ưu tiên có ít nhất 1 ngày nghỉ hoàn toàn trong tuần (từ Thứ 2 đến Thứ 7)
    private double penaltyNoFullRestDay(Map<Integer, List<ClassSession>> sessionsByDay) {
        int teachingDays = sessionsByDay.size();
        int totalPossibleDays = 6; // Thứ 2 đến Thứ 7
        int restDays = totalPossibleDays - teachingDays;
        if (restDays == 0) {
            return 120; // Không có ngày nghỉ nào → phạt nặng
        } else if (restDays >= 2) {
            return -20; // Có từ 2 ngày nghỉ trở lên → thưởng nhẹ (có thể để 0 nếu không muốn thưởng)
        }
        return 0; // Có đúng 1 ngày nghỉ → lý tưởng, không phạt
    }

    // S15: Không xếp lab vào ca 4
    private double penaltyLabInPeriod4(List<ClassSession> sessions) {
        double penalty = 0;
        for (ClassSession cs : sessions) {
            if (cs.getRoom().isLab() && cs.getTimeSlot().getPeriod() == 4) {
                penalty += 80;
            }
        }
        return penalty;
    }
}