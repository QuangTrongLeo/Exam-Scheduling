package model;

public class Building {
    private int id;       // 1, 2, 3, 4
    private String name;  // HD, TV, CT, RD

    public Building(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getter, setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
