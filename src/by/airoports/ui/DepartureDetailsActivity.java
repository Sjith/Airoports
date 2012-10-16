package by.airoports.ui;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import by.airoports.R;
import by.airoports.item.DepartureDetails;

public class DepartureDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_departure_details);
		DepartureDetails parcelableExtra = getIntent().getParcelableExtra(
				DepartureDetails.class.getSimpleName());

		TextView flight = (TextView) findViewById(R.id.flight);
		flight.setText(parcelableExtra.getFlight());
		TextView time = (TextView) findViewById(R.id.time);
		time.setText(parcelableExtra.getTime());
		TextView timeInFact = (TextView) findViewById(R.id.timeInFact);
		timeInFact.setText(parcelableExtra.getTimeInFact());

		// TODO parse time
		LocalDate now = new LocalDate();
		DateTimeFormatter parser1 = DateTimeFormat.forPattern("dd.MM.yyyy");
		LocalDate date = new LocalDate(LocalDate.parse(
				parcelableExtra.getDate(), parser1));
		Days daysBetween = Days.daysBetween(now, date);
		TextView timeOut = (TextView) findViewById(R.id.timeOut);
		if (daysBetween.getDays() == 0) {
			String format = String.format("%s часов", 5);
			timeOut.setText(format);
		} else {
			String format = "";
			int days = daysBetween.getDays();
			if (days == 1) {
				format = String.format("%s День  %s часов",
						daysBetween.getDays(), 2);
			}
			format = String
					.format("%s Дня  %s часов", daysBetween.getDays(), 2);
			timeOut.setText(format);
		}
		// TODO calculate time OUt
		TextView flightFrom = (TextView) findViewById(R.id.destination);
		flightFrom.setText(parcelableExtra.getDestination());
		TextView type = (TextView) findViewById(R.id.type);
		type.setText(parcelableExtra.getType());
		TextView status = (TextView) findViewById(R.id.status);
		status.setText(parcelableExtra.getStatus());// TODO create correct font
	}
}
