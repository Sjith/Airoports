package by.airoports.ui;

import by.airoports.R;
import by.airoports.customview.Calendar;
import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AiroportsTimeTable extends Activity {

	public static Intent buildIntent(Context context){
		return new Intent(context, AiroportsTimeTable.class);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);				
	}
	
	
}
