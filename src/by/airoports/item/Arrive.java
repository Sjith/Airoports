package by.airoports.item;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Arrive {

	private final String flight;
	private final String time;
	private final String timeInFact;
	private final String flightFrom;
	private final String status;
	private final String type;

	public Arrive(JSONObject object, List<String> keys) throws JSONException {
		flight = object.getString(keys.get(0));
		flightFrom = object.getString(keys.get(1));
		time = object.getString(keys.get(2));
		timeInFact = object.getString(keys.get(3));
		type = object.getString(keys.get(4));
		status = object.getString(keys.get(5));
	}

	public String getFlight() {
		return flight;
	}

	public String getTime() {
		return time;
	}

	public String getTimeInFact() {
		return timeInFact;
	}

	public String getType() {
		return type;
	}
	
	public String getFlightFrom() {
		return flightFrom;
	}

	public String getStatus() {
		return status;
	}

}
