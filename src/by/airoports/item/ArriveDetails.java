package by.airoports.item;

import android.os.Parcel;
import android.os.Parcelable;

public class ArriveDetails implements Parcelable {

	private final String flight;
	private final String time;
	private final String timeInFact;
	private final String flightFrom;
	private final String type;
	private final String status;

	private ArriveDetails(Parcel p) {
		flight = p.readString();
		time = p.readString();
		timeInFact = p.readString();
		flightFrom = p.readString();
		type = p.readString();
		status = p.readString();
	}

	public ArriveDetails(Arrive arrive) {
		flight = arrive.getFlight();
		time = arrive.getTime();
		timeInFact = arrive.getTimeInFact();
		flightFrom = arrive.getFlightFrom();
		type = arrive.getType();
		status = arrive.getStatus();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(flight);
		dest.writeString(time);
		dest.writeString(timeInFact);
		dest.writeString(type);
		dest.writeString(status);
	}

	public static final Parcelable.Creator<ArriveDetails> CREATOR = new Parcelable.Creator<ArriveDetails>() {
		public ArriveDetails createFromParcel(Parcel in) {
			return new ArriveDetails(in);
		}

		public ArriveDetails[] newArray(int size) {
			return new ArriveDetails[size];
		}
	};

	public String getFlight() {
		return flight;
	}

	public String getTime() {
		return time;
	}

	public String getTimeInFact() {
		return timeInFact;
	}

	public String getFlightFrom() {
		return flightFrom;
	}


	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

}
