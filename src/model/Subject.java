package model;

public class Subject {
	private int id;
    private String name;
    private int credits;
    private boolean isCore;
    private boolean isCompulsory;
    private int theoryLessons;
    private int labLessons;

    public Subject(int id, String name, int credits, boolean isCore, boolean isCompulsory, int theoryLessons, int labLessons) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.isCore = isCore;
        this.isCompulsory = isCompulsory;
        this.theoryLessons = theoryLessons;
        this.labLessons = labLessons;
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

	public boolean isCore() {
		return isCore;
	}

	public void setCore(boolean isCore) {
		this.isCore = isCore;
	}

	public boolean isCompulsory() {
		return isCompulsory;
	}

	public void setCompulsory(boolean isCompulsory) {
		this.isCompulsory = isCompulsory;
	}

	public int getTheoryLessons() {
		return theoryLessons;
	}

	public void setTheoryLessons(int theoryLessons) {
		this.theoryLessons = theoryLessons;
	}

	public int getLabLessons() {
		return labLessons;
	}

	public void setLabLessons(int labLessons) {
		this.labLessons = labLessons;
	}

	@Override
	public String toString() {
		return "Subject: id = " + id + "\t name = " + name + "\t credits = " + credits + "\t isCore = " + isCore
				+ "\t isCompulsory = " + isCompulsory + "\t theoryLessons = " + theoryLessons + "\t labLessons = " + labLessons;
	}

//	@Override
//	public String toString() {
//		return "Subject: id = " + id + "\t name = " + name + "\t credits = " + credits + "\t isCore = " + isCore
//				+ "\t isCompulsory = " + isCompulsory + "\t hasLab = " + hasLab;
//	}
	
	

}
