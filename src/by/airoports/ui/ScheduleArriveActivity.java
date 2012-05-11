package by.airoports.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import by.airoports.util.HtmlHelper;

public class ScheduleArriveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);

		long start = System.currentTimeMillis();
		HtmlHelper airoport = new HtmlHelper("http://airport.by/timetable/online");
		String schedule = airoport.getSchedule();
		// TODO calc parse data;
	}

	private void onDeparturesClick(View v) {
		// TODO GO TO Departures
	}
}
