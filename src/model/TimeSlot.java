package model;

public class TimeSlot {
    private int id;     // 1 -> 48
    private int week;   // 1 -> 2
    private int day;    // 2 -> 7
    private int slot;   // 1 -> 4

    public TimeSlot(int id, int week, int day, int slot) {
        this.id = id;
        this.week = week;
        this.day = day;
        this.slot = slot;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
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
        return "TimeSlot: id = " + id + "\t week = " + week + "\t day = " + day + "\t slot = " + slot;
    }

}
