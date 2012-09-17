package by.airoports.ui;

import java.util.List;

import org.joda.time.LocalDate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import by.airoports.R;

import com.google.common.collect.ImmutableList;

public class SearchFlightActivity extends Activity {

	private Spinner airoportSpinner;
	private Button search;
	private Spinner dateSpinner;
	private RadioGroup schedule;

	private final static LocalDate date = new LocalDate();
	public static List<LocalDate> dates = ImmutableList.of(date.minusDays(2),
			date.minusDays(1), date, date.plusDays(1), date.plusDays(2));
	
	public static Intent buildIntent(Context context) {
		return new Intent(context,SearchFlightActivity.class);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_search_flight);
		search = (Button) findViewById(R.id.search);
		airoportSpinner = (Spinner) findViewById(R.id.airoport);
		dateSpinner = (Spinner) findViewById(R.id.date);
		schedule = (RadioGroup) findViewById(R.id.schedule);
			
		SpinDateAdapter dateAdapter = new SpinDateAdapter(this,
	            android.R.layout.simple_spinner_item,
	            new String[]{"1","2"});
		
		int checkedRadioButton = schedule.getCheckedRadioButtonId();
		dateSpinner.setAdapter(dateAdapter);		
		String radioButtonSelected = "";		
		switch (checkedRadioButton) {
		case R.id.arrive:
			radioButtonSelected = "arrive";
			break;
		case R.id.departure:
			radioButtonSelected = "departure";
			break;
		}
		
		super.onCreate(savedInstanceState);
	}

	private class SpinDateAdapter extends ArrayAdapter<String>{

	    // Your sent context
	    private Context context;
	    // Your custom values for the spinner (User)
	    private String[] values;

	    public SpinDateAdapter(Context context, int textViewResourceId,
	    		String[] values) {
	        super(context, textViewResourceId, values);
	        this.context = context;
	        this.values = values;
	    }

	    public int getCount(){
	       return values.length;
	    }

	    public String getItem(int position){
	       return values[position];
	    }

	    public long getItemId(int position){
	       return position;
	    }


	    // And the "magic" goes here
	    // This is for the "passive" state of the spinner
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
	        TextView label = new TextView(context);
	        label.setTextColor(Color.BLACK);
	        // Then you can get the current item using the values array (Users array) and the current position
	        // You can NOW reference each method you has created in your bean object (User class)	        

	        // And finally return your dynamic (or custom) view for each spinner item
	        return label;
	    }

	    // And here is when the "chooser" is popped up
	    // Normally is the same view, but you can customize it if you want
	    @Override
	    public View getDropDownView(int position, View convertView,
	            ViewGroup parent) {
	        TextView label = new TextView(context);
	        label.setTextColor(Color.BLACK);
	        return label;
	    }
	}
}
