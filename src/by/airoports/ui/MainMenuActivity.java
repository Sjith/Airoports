package by.airoports.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import by.airoports.R;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	public void goToSearch(View w){
		startActivity(SearchFlightActivity.buildIntent(this));
	}
	
	public void goToTimeTable(View w){
		startActivity(SearchTimeTableActivity.buildIntent(this));
	}
}
