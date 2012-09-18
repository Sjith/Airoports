package by.airoports.ui;

import java.util.List;

import org.joda.time.LocalDate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import by.airoports.R;
import static by.airoports.app.Constants.TAG;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

public class SearchFlightActivity extends Activity {

	private Spinner airoportSpinner;
	private Button search;
	private Spinner dateSpinner;
	private RadioGroup schedule;

	private final static LocalDate date = new LocalDate();
	public final static int AIROPORT_SELECT = 5;
	public final static String AIROPORT_NAME = "AIROPORT_NAME";
	public static List<LocalDate> dates = ImmutableList.of(date.minusDays(2),
			date.minusDays(1), date, date.plusDays(1), date.plusDays(2));

	public static Intent buildIntent(Context context) {
		return new Intent(context, SearchFlightActivity.class);
	}

	private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				startActivityForResult(
						AiroportsActivity.buildIntent(v.getContext()),
						AIROPORT_SELECT);
			}
			return true;
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == AIROPORT_SELECT) {
			String airoportName = data.getStringExtra(AIROPORT_NAME);
			String[] array = new String[1];
			array[0] = airoportName;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, array);
			airoportSpinner.setAdapter(adapter);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_search_flight);
		initInterfaces();
		initListeners();

		super.onCreate(savedInstanceState);
	}

	private void initInterfaces() {
		search = (Button) findViewById(R.id.search);
		airoportSpinner = (Spinner) findViewById(R.id.airoport);
		dateSpinner = (Spinner) findViewById(R.id.date);
		schedule = (RadioGroup) findViewById(R.id.schedule);
	
		SpinDateAdapter dateAdapter = new SpinDateAdapter(this,
				android.R.layout.simple_spinner_item,
				dates.toArray(new LocalDate[dates.size()]));
		dateSpinner.setAdapter(dateAdapter);
	
	}

	private void initListeners() {
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String airoport = (String) airoportSpinner.getSelectedItem();
				LocalDate date = (LocalDate) dateSpinner.getSelectedItem();
				int checkedRadioButton = schedule.getCheckedRadioButtonId();				
				int radioSelected = 0;
				switch (checkedRadioButton) {
				case R.id.arrive:					
					radioSelected = 0;
					break;
				case R.id.departure:					
					radioSelected = 1;
					break;
				}							
				String formatDate = date.toString("dd.MM.yyyy");
				int i = airoport.indexOf('(');
				int j = airoport.indexOf(')');
				String substring = airoport.substring(i + 1, j);				
				String url = String
						.format("http://belavia.by/table/?siteid=1&id=5&departure=%d&airport=%s&date=%s",
								radioSelected, substring, formatDate);
				// TODO send to Arrive/Departure activity
				startActivity(ScheduleArriveActivity.buildIntent(v.getContext()));
			}
		});

		airoportSpinner.setOnTouchListener(spinnerOnTouch);
	}

	private class SpinDateAdapter extends ArrayAdapter<LocalDate> {

		// Your sent context
		private Context context;
		// Your custom values for the spinner (User)
		private LocalDate[] values;

		public SpinDateAdapter(Context context, int textViewResourceId,
				LocalDate[] values) {
			super(context, textViewResourceId);
			this.context = context;
			this.values = values;
		}

		@Override
		public int getCount() {
			return values.length;
		}

		@Override
		public LocalDate getItem(int position) {
			return values[position];
		}

		public long getItemId(int position) {
			return position;
		}

		// And the "magic" goes here
		// This is for the "passive" state of the spinner
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// I created a dynamic TextView here, but you can reference your own
			// custom layout for each spinner item
			TextView label = new TextView(context);
			label.setTextColor(Color.BLACK);
			label.setText(values[position].toString());
			// Then you can get the current item using the values array (Users
			// array) and the current position
			// You can NOW reference each method you has created in your bean
			// object (User class)

			// And finally return your dynamic (or custom) view for each spinner
			// item
			return label;
		}

		// And here is when the "chooser" is popped up
		// Normally is the same view, but you can customize it if you want
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			TextView label = new TextView(context);
			label.setTextColor(Color.BLACK);
			label.setText(values[position].toString());
			return label;
		}
	}
}
