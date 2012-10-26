package by.airoports.ui;

import by.airoports.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AiroportsContactsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services);
	}

	public static Intent buildIntent(Context context) {
		return new Intent(context, AiroportsContactsActivity.class);		
	}

	

}
