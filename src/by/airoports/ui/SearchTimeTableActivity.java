package by.airoports.ui;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import by.airoports.R;
import by.airoports.util.AiroportsTableUtils;

public class SearchTimeTableActivity extends Activity {

	public static final String FROM_AIROPORT = "FROM_AIROPORT";
	public static final int TABLE_RESULT = 5;
	public static final String TIME_TABLE_URL = "TIME_TABLE_URL";
	public static final String TIME_TABLE_AIROPORT_NAME = "TIME_TABLE_AIROPORT_NAME`";

	private Spinner fromAiroports;
	private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				startActivityForResult(
						AiroportsDataActivity.buildIntent(v.getContext()),
						TABLE_RESULT);
			}
			return true;
		}
	};

	public static Intent buildIntent(Context context) {
		return new Intent(context, SearchTimeTableActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_timetable);
		fromAiroports = (Spinner) findViewById(R.id.from_airoport);

		final Spinner toAiroports = (Spinner) findViewById(R.id.to_airoport);
		ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "Минск" });
		toAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toAiroports.setAdapter(toAdapter);

		final RadioGroup schedule = (RadioGroup) findViewById(R.id.schedule);

		fromAiroports.setOnTouchListener(spinnerOnTouch);
		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String destinationItem = (String) toAiroports.getSelectedItem();
				String airoportName = (String) fromAiroports.getSelectedItem();
				if (airoportName == null || destinationItem == null) {
					String message = getString(R.string.error_search_timetable);
					Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT)
							.show();
					return;
				}
				int checkedRadioButton = schedule.getCheckedRadioButtonId();
				int radioSelected = 0;
				switch (checkedRadioButton) {
				case R.id.oneway:
					radioSelected = 0;
					break;
				case R.id.twoways:
					radioSelected = 1;
					break;
				default:
					break;
				}
				Map<String, String> airoports = AiroportsTableUtils
						.getAiroports();
				String key = airoports.get(airoportName);

				String url = String
						.format("http://belavia.by/time-table/?siteid=1&id=3&backflight=%d&ttCitySource=%s&ttCityDestination=MSQ",
								radioSelected, key);
				if (radioSelected == 0) {
					startActivity(TimeTableOneWayActivity.buildIntent(
							SearchTimeTableActivity.this, url,airoportName));
				} else if (radioSelected == 1) {
					// TODO go to back activity
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == TABLE_RESULT) {
			String airoportName = data.getStringExtra(FROM_AIROPORT);
			String[] array = new String[1];
			array[0] = airoportName;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, array);
			fromAiroports.setAdapter(adapter);
		}
	};
}
