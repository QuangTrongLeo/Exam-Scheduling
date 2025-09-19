package model;

public class TimeSlot {
    private int id;   // 1 -> 24
    private int day;  // 2 -> 7
    private int slot; // 1 -> 4
	public TimeSlot(int id, int day, int slot) {
		super();
		this.id = id;
		this.day = day;
		this.slot = slot;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	@Override
	public String toString() {
		return "TimeSlot [id=" + id + ", day=" + day + ", slot=" + slot + "]";
	}

    
}
