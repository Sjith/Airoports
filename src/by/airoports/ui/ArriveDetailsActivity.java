package by.airoports.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import by.airoports.R;
import by.airoports.item.ArriveDetails;

public class ArriveDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arrive_details);
		ArriveDetails parcelableExtra = getIntent().getParcelableExtra(
				ArriveDetails.class.getSimpleName());
		TextView flight = (TextView) findViewById(R.id.flight);
		flight.setText(parcelableExtra.getFlight());
		TextView status = (TextView) findViewById(R.id.status);
		status.setText(parcelableExtra.getStatus());
		TextView time = (TextView) findViewById(R.id.time);
		time.setText(parcelableExtra.getTime());
		TextView timeInFact = (TextView) findViewById(R.id.timeInFact);
		timeInFact.setText(parcelableExtra.getTimeInFact());

		LocalDateTime now = new LocalDateTime();
		SimpleDateFormat hoursFormat = new SimpleDateFormat("dd.MM.yyyy:HH:mm");

		Date parseHours;
		try {
			parseHours = hoursFormat.parse(parcelableExtra.getDate() + ":"
					+ parcelableExtra.getTime());
		} catch (ParseException e) {
			parseHours = null;
			e.printStackTrace();
		}
		LocalDateTime date = new LocalDateTime(parseHours.getTime());
		Days daysBetween = Days.daysBetween(now, date);

		TextView timeOut = (TextView) findViewById(R.id.timeOut);
		LocalDateTime hours = new LocalDateTime(parseHours.getTime());
		int h = 0;
		int d = daysBetween.getDays();
		if (d == 0) {
			Hours hoursBetween = Hours.hoursBetween(now, hours);
			String format = String.format("%s часов", hoursBetween.getHours());
			timeOut.setText(format);
		} else {
			String format = "";
			if (d == 1) {
				Hours hoursBetween = Hours.hoursBetween(now, hours);
				format = String.format("%s День  %s часов",
						daysBetween.getDays(), hoursBetween.getHours() - 24);
				h = hoursBetween.getHours() - 24;				
			} else if (d > 1) {
				Hours hoursBetween = Hours.hoursBetween(now, hours);
				h = hoursBetween.getHours() - 48;
				format = formatTime(d, h);
			}
			timeOut.setText(format);
		}

		// TODO calculate time OUt
		TextView flightFrom = (TextView) findViewById(R.id.flightFrom);
		flightFrom.setText(parcelableExtra.getFlightFrom());

		TextView type = (TextView) findViewById(R.id.type);
		type.setText(parcelableExtra.getType());
	}

	private String formatTime(int day, int h) {
		StringBuilder builder = new StringBuilder();
		String days = "";
		String hours = "";
		if (day <= 4 || h <= 4) {
			days = "Дня";
			hours = "час(а)";
		}

		if (day >= 4) {
			days = "Дней";
		}

		if (h >= 4) {
			hours = "часов";
		}

		builder.append(day).append(" ").append(days).append(" ").append(h)
				.append(" ").append(hours);
		return builder.toString();
	}
}
