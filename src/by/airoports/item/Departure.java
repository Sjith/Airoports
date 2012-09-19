	package by.airoports.item;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Departure {
	private String company;
	private String flight;
	private String time;
	private String destination;
	private String sector;
	private String status;

	public Departure(JSONObject object, List<String> keys) throws JSONException {
		setFlight(object.getString(keys.get(0)));
		setDestination(object.getString(keys.get(1)));
		setTime((object.getString(keys.get(2))));
		setStatus(object.getString(keys.get(5)));
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany() {
		return company;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
