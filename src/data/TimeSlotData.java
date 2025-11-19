package data;

import java.util.ArrayList;
import java.util.List;

import model.TimeSlot;

public class TimeSlotData {
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
		for (TimeSlot tl : initTimeSlots()) {
			System.out.println(tl.toString());
		}
	}
}
