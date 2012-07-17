package by.airoports.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}
	
	public void goToSchedule(View w){
		startActivity(ScheduleArriveActivity.buildIntent(this));
	}
}
