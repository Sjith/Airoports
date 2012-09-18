package by.airoports.item;

import by.airoports.app.Constants;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ArriveDetails implements Parcelable {

	private final String company;
	private final String flight;
	private final String time;
	private final String timeInFact;
	private final String flightFrom;
	private final String status;

	private ArriveDetails(Parcel p) {
		company = p.readString();
		flight = p.readString();
		time = p.readString();
		timeInFact = p.readString();
		flightFrom = p.readString();
		status = p.readString();
	}

	public ArriveDetails(Arrive arrive) {
		company = arrive.getCompany();
		flight = arrive.getFlight();
		time = arrive.getTime();
		timeInFact = arrive.getTimeInFact();
		flightFrom = arrive.getFlightFrom();
		status = arrive.getStatus();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(company);
		dest.writeString(flight);
		dest.writeString(time);
		dest.writeString(timeInFact);
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

	public String getCompany() {
		return company;
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

	public String getFlightFrom() {
		return flightFrom;
	}


	public String getStatus() {
		return status;
	}

}
