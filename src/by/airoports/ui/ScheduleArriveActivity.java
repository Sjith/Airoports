package by.airoports.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleArriveActivity extends ListActivity {

	public static Intent buildIntent(Context context) {
		Intent intent = new Intent(context, ScheduleArriveActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);
		ArriveAdapter adapter = new ArriveAdapter();
		setListAdapter(adapter);
	}

	public void onDeparturesClick(View v) {
		startActivity(ScheduleDeparturesActivity.buildIntent(this));
		finish();
	}

	public void onArriveClick(View v) {
		// TODO GO TO Departures
	}


	private class ArriveAdapter extends BaseAdapter {

		private final String[] arrives = { "FirstRace", "Second" }; // TODO remove stub data;
		private final LayoutInflater inflater;

		public ArriveAdapter() {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return arrives.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item_arrive_schedule, null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView.findViewById(R.id.companyName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(arrives[position]);
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView textView;
	}
}
