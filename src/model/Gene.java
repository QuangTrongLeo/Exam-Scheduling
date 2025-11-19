package model;

import java.util.List;

public class Gene {
	private Lecturer lecturer;
    private List<ClassSession> classSessions;
	public Gene(Lecturer lecturer, List<ClassSession> classSessions) {
		super();
		this.lecturer = lecturer;
		this.classSessions = classSessions;
	}

	public Gene() {
		super();
	}

	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public List<ClassSession> getClassSessions() {
		return classSessions;
	}
	public void setClassSessions(List<ClassSession> classSessions) {
		this.classSessions = classSessions;
	}
	@Override
	public String toString() {
		return "LecturerSchedule: lecturer = " + lecturer.getName() + "\n classSessions=" + classSessions;
	}
    
}
