package data;

import model.*;
import java.util.*;

public class DataInitializer {

    // KHỞI TẠO DANH SÁCH MÔN HỌC
    public static List<Subject> initSubjects() {
        List<Subject> subjects = new ArrayList<>();

//        subjects.add(new Subject(214463, "Nhập môn trí tuệ nhân tạo", 4, true));
//        subjects.add(new Subject(214293, "Thực tập lập trình trên thiết bị di động", 3, false));
//        subjects.add(new Subject(214379, "Đảm bảo chất lượng và kiểm thử phần mềm", 3, false));
//        subjects.add(new Subject(214388, "Lập trình Front End", 4, false));
//        subjects.add(new Subject(214485, "Data Mining", 4, false));
//        subjects.add(new Subject(214491, "Data Warehouse", 3, false));

        return subjects;
    }

    // KHỞI TẠO DANH SÁCH CA HỌC
    public static List<TimeSlot> initTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        for (int day = 2; day <= 7; day++) {
			for (int period = 1; period <= 4; period++) {
				slots.add(new TimeSlot(day, period));
			}
		}
        return slots;
    }
    
    public static void main(String[] args) {
		List<Subject> subjects = initSubjects();
		List<TimeSlot> timeSlots = initTimeSlots();
		
		System.out.println("========== SUBJECTS ==========");
		for (Subject subject : subjects) {
			System.out.println(subject.toString());
		}
		
		System.out.println();
		
		System.out.println("========== TIMESLOTS ==========");
		for (TimeSlot timeSlot : timeSlots) {
			System.out.println(timeSlot.toString());
		}
	}
}
