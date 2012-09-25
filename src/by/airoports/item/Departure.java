package by.airoports.item;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Departure {	
	private final String flight;
	private final String time;
	private final String timeInFact;
	private final String destination;
	private final String type;
	private final String status;

	public Departure(JSONObject object, List<String> keys) throws JSONException {		
		flight = object.getString(keys.get(0));
		destination = object.getString(keys.get(1));
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

	public String getDestination() {
		return destination;
	}


	public String getType() {
		return type;
	}


	public String getStatus() {
		return status;
	}


}
