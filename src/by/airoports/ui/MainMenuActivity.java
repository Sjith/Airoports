package by.airoports.ui;

import by.airoports.R;
import by.airoports.app.Constants;
import by.airoports.contract.AiroportContract;
import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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
	
	public void goToSearch(View w){
		startActivity(SearchFlightActivity.buildIntent(this));
	}
}
