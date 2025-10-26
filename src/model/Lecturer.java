package model;

import java.util.List;

public class Lecturer {
	private int id;
    private String name;
    private List<Subject> subjects;

	public Lecturer(int id, String name, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.subjects = subjects;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "Lecturer: id = " + id + "\t name = " + name + "\n\tsubjects = " + subjects;
	}
    
}
