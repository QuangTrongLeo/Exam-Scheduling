package model;

public class Room {
	private String id;
    private String name;
    private boolean isLab;
    
	public Room(String id, String name, boolean isLab) {
		super();
		this.id = id;
		this.name = name;
		this.isLab = isLab;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public void setLab(boolean isLab) {
		this.isLab = isLab;
	}

	@Override
	public String toString() {
		return "Room: id = " + id + ",\tname = " + name + "\tisLab = " + isLab;
	}
       
}
