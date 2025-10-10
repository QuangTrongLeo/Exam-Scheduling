package model;

public class Subject {
	private int id;
    private String name;
    private int credits;        // số tín chỉ (2-4)
    private boolean required; // true = môn bắt buộc, false = môn tự chọn
    
	public Subject(int id, String name, int credits, boolean required) {
		super();
		this.id = id;
		this.name = name;
		this.credits = credits;
		this.required = required;
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

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public String toString() {
		return "Subject: id = " + id + "\t name = " + name + "\t\t\t credits = " + credits + "\t required = " + required;
	}

}
