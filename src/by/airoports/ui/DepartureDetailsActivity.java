package by.airoports.ui;

import by.airoports.R;
import by.airoports.app.Constants;
import by.airoports.item.ArriveDetails;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

public class DepartureDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_departure_details);
		ArriveDetails parcelableExtra = getIntent().getParcelableExtra(
				ArriveDetails.class.getSimpleName());

		TextView company = (TextView) findViewById(R.id.company);
		company.setText(parcelableExtra.getCompany());
		TextView flight = (TextView) findViewById(R.id.flight);
		flight.setText(parcelableExtra.getFlight());
		TextView time = (TextView) findViewById(R.id.time);
		time.setText(parcelableExtra.getTime());
		TextView timeInFact = (TextView) findViewById(R.id.timeInFact);
		timeInFact.setText(parcelableExtra.getTimeInFact());
		TextView timeOut = (TextView) findViewById(R.id.timeOut);
		// TODO calculate time OUt
		TextView flightFrom = (TextView) findViewById(R.id.flightFrom);
		flightFrom.setText(parcelableExtra.getFlightFrom());
		
		TextView sector = (TextView) findViewById(R.id.sector);
		sector.setText(parcelableExtra.getSector());
		TextView status = (TextView) findViewById(R.id.status);
		status.setText(parcelableExtra.getStatus());
	}
}
