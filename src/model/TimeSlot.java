package model;

import java.util.Objects;

public class TimeSlot {
    private int day;    // 2 -> 7
    private int period;   // 1 -> 4
    
    public TimeSlot() {}

	public TimeSlot(int day, int period) {
        this.day = day;
        this.period = period;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(day, period);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSlot other = (TimeSlot) obj;
		return day == other.day && period == other.period;
	}

	@Override
    public String toString() {
        return "TimeSlot: day = " + day + "\t period = " + period;
    }

}
