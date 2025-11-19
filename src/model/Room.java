package model;

public class Room {
	private int id;
    private String name;
    private int capacity;
    private boolean isLab;
    
	public Room(int id, String name, int capacity, boolean isLab) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.isLab = isLab;
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

	public boolean isLab() {
		return isLab;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setLab(boolean isLab) {
		this.isLab = isLab;
	}

	@Override
	public String toString() {
		return "Room: id = " + id + "\t name = " + name + "\t capacity = " + capacity + "\t isLab = " + isLab;
	}
        
}
