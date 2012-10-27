package by.airoports.ui;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDateTime;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import by.airoports.R;

import com.google.common.collect.Lists;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		LocalDateTime now = new LocalDateTime();
		List<String> list = getWeekDays();
		int dayOfWeek = now.getDayOfWeek();
		TextView text = (TextView) findViewById(R.id.date);
		String day = list.get(dayOfWeek - 1);
		day = day.replace(day.charAt(0), Character.toUpperCase(day.charAt(0)));
		text.setText(now.toString("dd.MMMM.yyyy", new Locale("RU", "ru")) + " "
				+ day);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public void goToSearch(View w) {
		startActivity(SearchFlightActivity.buildIntent(this));
	}

	public void goToTimeTable(View w) {
		startActivity(SearchTimeTableActivity.buildIntent(this));
	}

	public void goToServices(View w) {
		startActivity(AiroportsServicesActivity.buildIntent(this));
	}

	private List<String> getWeekDays() {
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(new Locale(
				"RU", "ru"));
		String[] weekdays = dateFormatSymbols.getWeekdays();
		List<String> list = Lists.newArrayList();
		for (int i = 1; i < weekdays.length; i++) {
			String weekday = weekdays[i];
			if (!TextUtils.isEmpty(weekday)) {
				list.add(weekday);
			}
		}
		String string = list.get(0);
		list.remove(0);
		list.add(string);
		return list;
	}
}
