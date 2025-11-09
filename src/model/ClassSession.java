package model;

public class ClassSession {
	private Subject subject;
    private Room room;
    private TimeSlot timeSlot;
    
	public ClassSession(Subject subject, Room room, TimeSlot timeSlot) {
		super();
		this.subject = subject;
		this.room = room;
		this.timeSlot = timeSlot;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public String toString() {
		return "Lesson: subject = " + subject + "\troom = " + room + "\ttimeSlot = " + timeSlot;
	}
    
}
