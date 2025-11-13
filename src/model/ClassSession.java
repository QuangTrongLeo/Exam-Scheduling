package model;

public class ClassSession {
	private Subject subject;
    private Lecturer lecturer;
    private Room room;
    private TimeSlot timeSlot;
    
	public ClassSession(Subject subject, Lecturer lecturer, Room room, TimeSlot timeSlot) {
		super();
		this.subject = subject;
		this.lecturer = lecturer;
		this.room = room;
		this.timeSlot = timeSlot;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
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
		return "ClassSession: subject = " + subject + "\tlecture = " + lecturer + "\troom = " + room + "\ttimeSlot = " + timeSlot;
	}
    
}
