package by.airoports.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import by.airoports.R;

public class SearchTimeTableActivity extends Activity {

	public static String FROM_AIROPORT = "FROM_AIROPORT";
	public static int TABLE_RESULT = 5;

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
		
		Spinner toAiroports = (Spinner) findViewById(R.id.to_airoport);		
		ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "Минск"});
		toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toAiroports.setAdapter(toAdapter);
		
		fromAiroports.setOnTouchListener(spinnerOnTouch);
		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(TimeTableActivity.buildIntent(v.getContext()));
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
