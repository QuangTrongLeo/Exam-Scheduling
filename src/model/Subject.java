package model;

public class Subject {
	private int id;
    private String name;
    private int credits;        // số tín chỉ (2-4)
    private boolean compulsory; // true = môn bắt buộc, false = môn tự chọn
    private int year;
    
	public Subject(int id, String name, int credits, boolean compulsory, int year) {
		super();
		this.id = id;
		this.name = name;
		this.credits = credits;
		this.compulsory = compulsory;
		this.year = year;
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

	public boolean isCompulsory() {
		return compulsory;
	}

	public void setCompulsory(boolean compulsory) {
		this.compulsory = compulsory;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Subject: id = " + id + "\t name = " + name + "\t\t\t credits = " + credits + "\t compulsory = " + compulsory
				+ "\t year = " + year;
	}

}
