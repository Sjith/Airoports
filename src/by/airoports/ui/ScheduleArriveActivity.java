package by.airoports.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;

public class ScheduleArriveActivity extends ListActivity {

	public static Intent buildIntent(Context context) {
		Intent intent = new Intent(context, ScheduleArriveActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);

		findViewById(R.id.tab_arrive).setPressed(true);

		ArrayList<List<String>> arrayList = new ArrayList<List<String>>();
		arrayList.add(new ArrayList<String>());
		ArriveAdapter adapter = new ArriveAdapter(arrayList);
		setListAdapter(adapter);
		// ArriveScheduleLoader scheduleLoader = new ArriveScheduleLoader(this, new ProgressDialogInfo("LOADER",
		// "Load a schedule", true, false));
		// scheduleLoader.execute("http://airport.by/timetable/online");
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

		private final List<List<String>> arrives;
		private final LayoutInflater inflater;

		public ArriveAdapter(List<List<String>> arrives) {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.arrives = arrives;
		}

		@Override
		public int getCount() {
			return arrives.size();
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
				holder.flight = (TextView) convertView.findViewById(R.id.flight);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.flight.setText("12312321-vs");
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView time;
		public TextView timeInFact;
		public TextView flight;
		public TextView from;
		public TextView arrive;
		public TextView sector;

	}

	private static class ArriveScheduleLoader extends
			ProgressAsyncTask<String, Void, List<List<String>>, ScheduleArriveActivity> {

		public ArriveScheduleLoader(ScheduleArriveActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);

		}

		@Override
		protected List<List<String>> doInBackground(ScheduleArriveActivity target, String... params) {
			List<List<String>> arriveSchedule = null;
			try {
				HtmlHelper arrive = new HtmlHelper(params[0]);
				arriveSchedule = arrive.getArriveSchedule();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arriveSchedule;
		}

		@Override
		protected void onPostExecute(ScheduleArriveActivity target, List<List<String>> result) {

			super.onPostExecute(target, result);
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
					holder.textView = (TextView) convertView.findViewById(R.id.flight);
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
}
