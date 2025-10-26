package model;

import java.util.List;

public class Gene {
	private Lecturer lecturer;
    private List<Lesson> lessons;
    
	public Gene(Lecturer lecturer, List<Lesson> lessons) {
		super();
		this.lecturer = lecturer;
		this.lessons = lessons;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	@Override
	public String toString() {
		return "Gene: lecturer = " + lecturer + "\tlessons = " + lessons;
	} 
    
    
}
