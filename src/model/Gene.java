package model;

public class Gene {
	private Subject subject;
    private TimeSlot theoryTimeSlot;
    private TimeSlot practiceTimeSlot;
    
	public Gene(Subject subject, TimeSlot theoryTimeSlot, TimeSlot practiceTimeSlot) {
		super();
		this.subject = subject;
		this.theoryTimeSlot = theoryTimeSlot;
		this.practiceTimeSlot = practiceTimeSlot;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public TimeSlot getTheoryTimeSlot() {
		return theoryTimeSlot;
	}

	public void setTheoryTimeSlot(TimeSlot theoryTimeSlot) {
		this.theoryTimeSlot = theoryTimeSlot;
	}

	public TimeSlot getPracticeTimeSlot() {
		return practiceTimeSlot;
	}

	public void setPracticeTimeSlot(TimeSlot practiceTimeSlot) {
		this.practiceTimeSlot = practiceTimeSlot;
	}

}
