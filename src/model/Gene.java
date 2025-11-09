package model;

import java.util.List;

public class Gene {
	private Lecturer lecturer;
    private List<ClassSession> sessions;
    
	public Gene(Lecturer lecturer, List<ClassSession> sessions) {
		super();
		this.lecturer = lecturer;
		this.sessions = sessions;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public List<ClassSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<ClassSession> sessions) {
		this.sessions = sessions;
	}

	@Override
	public String toString() {
		return "Gene: lecturer = " + lecturer + "\t lessons = " + sessions;
	} 
    
    
}
