package by.airoports.item;

import java.util.List;

public class TimeTableInfo {

	private final List<boolean[]> saveWeekSchedule;
	private final List<String[]> saveWeekDescription;

	public TimeTableInfo(List<boolean[]> saveWeekSchedule, List<String[]> saveWeekDescription) {		
		this.saveWeekSchedule = saveWeekSchedule;
		this.saveWeekDescription = saveWeekDescription;
	}

	public List<boolean[]> getSaveWeekSchedule() {
		return saveWeekSchedule;
	}

	public List<String[]> getSaveWeekDescription() {
		return saveWeekDescription;
	}

}
