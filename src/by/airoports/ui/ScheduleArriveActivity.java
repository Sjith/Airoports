package by.airoports.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScheduleArriveActivity extends Activity {

	public static Intent buildIntent(Context context) {
		Intent intent = new Intent(context, ScheduleArriveActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);
	}

	public void onDeparturesClick(View v) {
		startActivity(ScheduleDeparturesActivity.buildIntent(this));
		finish();
	}

	public void onArriveClick(View v) {
		// TODO GO TO Departures
	}
}
