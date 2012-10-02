package by.airoports.item;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartureDetails implements Parcelable {
	private final String flight;
	private final String time;
	private final String timeInFact;
	private final String destination;
	private final String type;
	private final String status;
	private final String date;

	public DepartureDetails(Departure departure, String date) {

		flight = departure.getFlight();
		time = departure.getTime();
		timeInFact = departure.getTimeInFact();
		destination = departure.getDestination();
		type = departure.getType();
		status = departure.getStatus();
		this.date = date;
	}

	private DepartureDetails(Parcel p) {
		flight = p.readString();
		time = p.readString();
		timeInFact = p.readString();
		destination = p.readString();
		type = p.readString();
		status = p.readString();
		date = p.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(flight);
		dest.writeString(time);
		dest.writeString(timeInFact);
		dest.writeString(destination);
		dest.writeString(type);
		dest.writeString(status);
		dest.writeString(date);
	}

	public static final Parcelable.Creator<DepartureDetails> CREATOR = new Parcelable.Creator<DepartureDetails>() {
		public DepartureDetails createFromParcel(Parcel in) {
			return new DepartureDetails(in);
		}

		public DepartureDetails[] newArray(int size) {
			return new DepartureDetails[size];
		}
	};

	public String getFlight() {
		return flight;
	}

	public String getTime() {
		return time;
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

	public String getTimeInFact() {
		return timeInFact;
	}

	public String getDate() {
		return date;
	}
}
