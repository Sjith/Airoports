package by.airoports.item;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartureDetails implements Parcelable {
	private final String company;
	private final String flight;
	private final String time;
	private final String destination;
	private final String sector;
	private final String status;

	public DepartureDetails(Departure departure) {
		company = departure.getCompany();
		flight = departure.getFlight();
		time = departure.getTime();
		destination = departure.getDestination();
		sector = departure.getSector();
		status = departure.getStatus();
	}

	private DepartureDetails(Parcel p) {
		company = p.readString();
		flight = p.readString();
		time = p.readString();
		destination = p.readString();
		sector = p.readString();
		status = p.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(company);
		dest.writeString(flight);
		dest.writeString(time);
		dest.writeString(destination);
		dest.writeString(sector);
		dest.writeString(status);
	}

	public static final Parcelable.Creator<DepartureDetails> CREATOR = new Parcelable.Creator<DepartureDetails>() {
		public DepartureDetails createFromParcel(Parcel in) {
			return new DepartureDetails(in);
		}

		public DepartureDetails[] newArray(int size) {
			return new DepartureDetails[size];
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

	public String getDestination() {
		return destination;
	}

	public String getSector() {
		return sector;
	}

	public String getStatus() {
		return status;
	}
}
