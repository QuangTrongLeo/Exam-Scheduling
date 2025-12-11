package model;

public class ConflictKey {
	private final int day;
    private final int period;
    private final int roomId;

    public ConflictKey(int day, int period, int roomId) {
        this.day = day;
        this.period = period;
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConflictKey that = (ConflictKey) o;
        return day == that.day && period == that.period && roomId == that.roomId;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + period;
        result = 31 * result + roomId;
        return result;
    }

}
