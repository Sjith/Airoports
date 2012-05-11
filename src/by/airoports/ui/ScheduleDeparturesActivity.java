package by.airoports.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScheduleDeparturesActivity extends Activity {

	public static Intent buildIntent(Context context) {
		Intent intent = new Intent(context, ScheduleDeparturesActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);
	}

	public void onDeparturesClick(View v) {

	}

	public void onArriveClick(View v) {
		startActivity(ScheduleArriveActivity.buildIntent(this));
		finish();
	}
}
