package model;

import java.util.List;

public class ExamSchedule {
	private Subject subject;
    private TimeSlot timeslot;
    private List<Room> rooms;
    
	public ExamSchedule(Subject subject, TimeSlot timeslot, List<Room> rooms) {
		super();
		this.subject = subject;
		this.timeslot = timeslot;
		this.rooms = rooms;
	}
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public TimeSlot getTimeslot() {
		return timeslot;
	}
	public void setTimeslot(TimeSlot timeslot) {
		this.timeslot = timeslot;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	@Override
	public String toString() {
		return "ExamSchedule [subject=" + subject + ", timeslot=" + timeslot + ", rooms=" + rooms + "]";
	} 
   
}
