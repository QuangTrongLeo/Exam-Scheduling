package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Subject {
	private int id;
    private String name;
    private int credits;        // số tín chỉ (2-4)
    private boolean isCore;        // Môn cốt lõi
    private boolean isCompulsory;  // Môn bắt buộc
    private boolean hasLab;
    
	public Subject(int id, String name, int credits, boolean isCore, boolean isCompulsory, boolean hasLab) {
		super();
		this.id = id;
		this.name = name;
		this.credits = credits;
		this.isCore = isCore;
		this.isCompulsory = isCompulsory;
		this.hasLab = hasLab;
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

	public boolean isHasLab() {
		return hasLab;
	}

	public void setHasLab(boolean hasLab) {
		this.hasLab = hasLab;
	}

	@Override
	public String toString() {
		return "Subject: id = " + id + "\t name = " + name + "\t credits = " + credits + "\t isCore = " + isCore
				+ "\t isCompulsory = " + isCompulsory + "\t hasLab = " + hasLab;
	}
	
	public Map<String, Integer> calculateSchedule() {
        int theory = 0;
        int lab = 0;

        if (isCompulsory && isCore && hasLab) {
            theory = randomBetween(2, 3);
            lab = theory + 2;
        } else if (isCompulsory && hasLab) {
            theory = randomBetween(2, 3);
            lab = theory + 1;
        } else if (!isCompulsory && hasLab) {
            theory = randomBetween(1, 2);
            lab = theory + 1;
        } else if (isCompulsory && !hasLab) {
            theory = 2;
            lab = 0;
        }

        Map<String, Integer> schedule = new HashMap<>();
        schedule.put("theory", theory);
        schedule.put("lab", lab);
        return schedule;
    }

    private int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
