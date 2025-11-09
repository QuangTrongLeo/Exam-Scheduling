package model;

public class SubjectClassCount {
	private Subject subject;
    private int theory;
    private int lab;
    
	public SubjectClassCount(Subject subject, int theory, int lab) {
		super();
		this.subject = subject;
		this.theory = theory;
		this.lab = lab;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getTheory() {
		return theory;
	}

	public void setTheory(int theory) {
		this.theory = theory;
	}

	public int getLab() {
		return lab;
	}

	public void setLab(int lab) {
		this.lab = lab;
	}
    
	@Override
    public String toString() {
        return subject.getName() + " - LT: " + theory + ", TH: " + lab;
    }
    
}
