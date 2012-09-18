package by.airoports.item;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import by.airoports.util.HtmlHelper;

public class Arrive {
	private String company;
	private String flight;
	private String time;
	private String timeInFact;
	private String flightFrom;	
	private String status;

	public Arrive(){
		
	}
	public Arrive(JSONObject object,List<String>keys) {
		try {
			setFlight(object.getString(keys.get(0)));
			setFlightFrom(object.getString(keys.get(1)));
			setTime((object.getString(keys.get(2))));
			setStatus(object.getString(keys.get(5)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getFlight() {
		return flight;
	}	
	
	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeInFact() {
		return timeInFact;
	}

	public void setTimeInFact(String timeInFact) {
		this.timeInFact = timeInFact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlightFrom() {
		return flightFrom;
	}

	public void setFlightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
