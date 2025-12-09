package model;

import java.util.ArrayList;
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
	
	public Gene cloneGene() {
        List<ClassSession> newSessions = new ArrayList<>();
        // Duyệt qua từng session và clone nó ra bản sao mới
        if (this.classSessions != null) {
            for (ClassSession cs : this.classSessions) {
                newSessions.add(cs.cloneSession());
            }
        }
        return new Gene(this.lecturer, newSessions);
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
