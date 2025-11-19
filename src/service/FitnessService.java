// File: service/FitnessService.java
package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Lecturer;
import model.Subject;
import model.TimeSlot;

public class FitnessService {

	private static final int MAX_PENALTY = 1000000; // Sẽ dùng sau cho hard constraints (hiện tại để 0)

	// Danh sách TimeSlot toàn bộ (Thứ 2 đến Thứ 7, Ca 1-4)
	private List<TimeSlot> allTimeSlots;

	public FitnessService() {
		allTimeSlots = new ArrayList<>();
		for (int day = 2; day <= 7; day++) {
			for (int period = 1; period <= 4; period++) {
				allTimeSlots.add(new TimeSlot(day, period));
			}
		}
	}

	public double calculateFitness(Individual individual) {
		double totalPenalty = 0;

		for (Gene gene : individual.getGenes()) {
			Lecturer lecturer = gene.getLecturer();
			List<ClassSession> sessions = gene.getClassSessions();

			// Nhóm các buổi học theo ngày
			Map<Integer, List<ClassSession>> sessionsByDay = sessions.stream()
					.collect(Collectors.groupingBy(cs -> cs.getTimeSlot().getDay()));

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
		double fitness = MAX_PENALTY - totalPenalty;
		return Math.max(0, fitness); // không âm
	}

	// S1 + S11: Ưu tiên dạy ít ngày (3-4 ngày), có ít nhất 1 ngày nghỉ hoàn toàn
	private int penaltyTeachingDays(Map<Integer, List<ClassSession>> sessionsByDay) {
		int teachingDays = sessionsByDay.size();
		int penalty = 0;
		if (teachingDays > 4)
			penalty += (teachingDays - 4) * 80; // dạy >4 ngày: phạt nặng
		if (teachingDays < 3)
			penalty += (3 - teachingDays) * 50; // dạy <3 ngày: cũng không tốt
		if (teachingDays == 6)
			penalty += 200; // dạy full tuần: phạt rất nặng
		if (sessionsByDay.size() < 6)
			penalty -= 50; // có ngày nghỉ: thưởng nhẹ
		return penalty;
	}

	// S2: Mỗi môn nên gói gọn trong ít ngày (tối đa 2 ngày)
	private int penaltySubjectSpread(Lecturer lecturer, List<ClassSession> sessions) {
		int penalty = 0;
		for (Subject subject : lecturer.getSubjects()) {
			Set<Integer> days = sessions.stream().filter(cs -> cs.getSubject().getId() == subject.getId())
					.map(cs -> cs.getTimeSlot().getDay()).collect(Collectors.toSet());
			if (days.size() > 2) {
				penalty += (days.size() - 2) * 60;
			}
		}
		return penalty;
	}

	// S3: Môn cốt lõi nên dạy ca 1-2
	private int penaltyCoreSubjectLateSlots(List<ClassSession> sessions) {
		int penalty = 0;
		for (ClassSession cs : sessions) {
			if (cs.getSubject().isCore() && cs.getTimeSlot().getPeriod() >= 3) {
				penalty += 70;
			}
		}
		return penalty;
	}

	// S4: Không dạy quá 3 ca liên tiếp trong 1 ngày
	private int penaltyConsecutivePeriods(Map<Integer, List<ClassSession>> sessionsByDay) {
		int penalty = 0;
		for (List<ClassSession> daySessions : sessionsByDay.values()) {
			Set<Integer> periods = daySessions.stream().map(cs -> cs.getTimeSlot().getPeriod())
					.collect(Collectors.toSet());
			if (periods.containsAll(Arrays.asList(1, 2, 3, 4))) {
				penalty += 150; // dạy full 4 ca: phạt nặng
			} else if (periods.size() >= 3) {
				// Kiểm tra có 3 ca liên tiếp không
				for (int i = 1; i <= 2; i++) {
					if (periods.contains(i) && periods.contains(i + 1) && periods.contains(i + 2)) {
						penalty += 80;
					}
				}
			}
		}
		return penalty;
	}

	// S5: Giảm khoảng trống giữa các ca
	private int penaltyIdleGaps(Map<Integer, List<ClassSession>> sessionsByDay) {
		int penalty = 0;
		for (List<ClassSession> daySessions : sessionsByDay.values()) {
			Set<Integer> periods = daySessions.stream().map(cs -> cs.getTimeSlot().getPeriod()).sorted()
					.collect(Collectors.toCollection(LinkedHashSet::new));

			List<Integer> periodList = new ArrayList<>(periods);
			for (int i = 0; i < periodList.size() - 1; i++) {
				int gap = periodList.get(i + 1) - periodList.get(i);
				if (gap > 1) {
					penalty += (gap - 1) * 40; // mỗi khoảng trống 1 ca: phạt 40
				}
			}
		}
		return penalty;
	}

	// S7: Cân bằng số ca mỗi ngày
	private int penaltyUnbalancedDailyLoad(Map<Integer, List<ClassSession>> sessionsByDay) {
		int penalty = 0;
		List<Integer> dailyCounts = sessionsByDay.values().stream()
				.map(day -> day.stream().map(cs -> cs.getTimeSlot().getPeriod()).collect(Collectors.toSet()).size())
				.collect(Collectors.toList());

		if (dailyCounts.isEmpty())
			return 0;

		double avg = dailyCounts.stream().mapToInt(Integer::intValue).average().orElse(0);
		for (int count : dailyCounts) {
			penalty += Math.abs(count - avg) * 30;
		}
		return (int) penalty;
	}

	// S11: Ưu tiên có ít nhất 1 ngày nghỉ hoàn toàn trong tuần (từ Thứ 2 đến Thứ 7)
	private int penaltyNoFullRestDay(Map<Integer, List<ClassSession>> sessionsByDay) {
		int teachingDays = sessionsByDay.size();
		int totalPossibleDays = 6; // Thứ 2 đến Thứ 7
		int restDays = totalPossibleDays - teachingDays;

		if (restDays == 0) {
			return 200; // Không có ngày nghỉ nào → phạt nặng
		} else if (restDays >= 2) {
			return -50; // Có từ 2 ngày nghỉ trở lên → thưởng nhẹ (có thể để 0 nếu không muốn thưởng)
		}
		return 0; // Có đúng 1 ngày nghỉ → lý tưởng, không phạt
	}

	// S15: Không xếp lab vào ca 4
	private int penaltyLabInPeriod4(List<ClassSession> sessions) {
		int penalty = 0;
		for (ClassSession cs : sessions) {
			if (cs.getRoom().isLab() && cs.getTimeSlot().getPeriod() == 4) {
				penalty += 100;
			}
		}
		return penalty;
	}
}