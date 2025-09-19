package model;

public class Room {
    private int id;            // 1, 2, 3, ...
    private Building building; // Tham chiếu tới tòa
    private int roomNumber;    // 101, 102, ...

    public Room(int id, Building building, int roomNumber) {
        this.id = id;
        this.building = building;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public Building getBuilding() {
        return building;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    // Helper method: hiển thị dạng "HD101"
    public String getFullName() {
        return building.getName() + roomNumber;
    }
}
