package data;

import java.util.ArrayList;
import java.util.List;
import model.Room;

public class RoomData {

    public static List<Room> initRooms() {
        List<Room> rooms = new ArrayList<>();
        int idCounter = 1;

        // === 6 phòng Lab (Lab01 -> Lab06), tất cả 40 ===
        for (int i = 1; i <= 6; i++) {
            rooms.add(new Room(idCounter++, "Lab0" + i, 40, true));
        }

        // === 24 phòng Rạng Đông (RD101 -> RD406) ===
        String[] rdFloors = {"1", "2", "3", "4"};
        for (String floor : rdFloors) {
            for (int i = 1; i <= 6; i++) {
                int capacity = (i == 6) ? 120 : 80; // x06 = 120, còn lại 80
                rooms.add(new Room(idCounter++, "RD" + floor + "0" + i, capacity, false));
            }
        }

        // === 9 phòng Tường Vi (TV101 -> TV303) ===
        String[] tvFloors = {"1", "2", "3"};
        for (String floor : tvFloors) {
            for (int i = 1; i <= 3; i++) {
                int capacity = (i == 3) ? 120 : 80; // x03 = 120, còn lại 80
                rooms.add(new Room(idCounter++, "TV" + floor + "0" + i, capacity, false));
            }
        }

        // === 9 phòng Hướng Dương (HD101 -> HD303) ===
        String[] hdFloors = {"1", "2", "3"};
        for (String floor : hdFloors) {
            for (int i = 1; i <= 3; i++) {
                int capacity = (i == 3) ? 120 : 80; // x03 = 120, còn lại 80
                rooms.add(new Room(idCounter++, "HD" + floor + "0" + i, capacity, false));
            }
        }

        return rooms;
    }

    public static void main(String[] args) {
        for (Room r : initRooms()) {
            System.out.println(r.toString());
        }
    }
}
