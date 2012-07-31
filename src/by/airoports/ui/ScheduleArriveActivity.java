package by.airoports.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import by.airoports.R;
import by.airoports.item.Arrive;
import by.airoports.item.ArriveDetails;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

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

		List<Arrive> l = new ArrayList<Arrive>();
		Arrive arrive = new Arrive();
		arrive.setCompany("BELAVIA");
		arrive.setFlight("Test flight");
		arrive.setTime("12:00");
		arrive.setTimeInFact("12:22");
		arrive.setFlightFrom("Moscow");
		l.add(arrive);
		ArriveAdapter adapter = new ArriveAdapter(l);
		setListAdapter(adapter);

		// ArriveScheduleLoader scheduleLoader = new ArriveScheduleLoader(
		// this,
		// new ProgressDialogInfo("LOADER", "Load a schedule", true, false));
		// scheduleLoader.execute("http://www.airport.by/timetable/online");
	}

	public void onDeparturesClick(View v) {
		startActivity(ScheduleDeparturesActivity.buildIntent(this));
		finish();
	}

	public void onArriveClick(View v) {
		// TODO GO TO Departures
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ArriveDetailsActivity.class);

		ArriveAdapter adapter = (ArriveAdapter) getListAdapter();
		Arrive item = adapter.getItem(position);
		ArriveDetails details = new ArriveDetails(item);
		intent.putExtra(ArriveDetails.class.getSimpleName(), details);
		startActivity(intent);

		super.onListItemClick(l, v, position, id);
	}

	private class ArriveAdapter extends BaseAdapter {

		private final List<Arrive> arrives;
		private final LayoutInflater inflater;

		public ArriveAdapter(List<Arrive> arrives) {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.arrives = arrives;
		}

		@Override
		public int getCount() {
			return arrives.size();
		}

		@Override
		public Arrive getItem(int position) {
			// TODO Auto-generated method stub
			return arrives.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.list_item_arrive_schedule, null);
				holder = new ViewHolder();
				holder.flight = (TextView) convertView
						.findViewById(R.id.flight);
				holder.time = (TextView) convertView
						.findViewById(R.id.arriveTime);
				holder.sector = (TextView) convertView
						.findViewById(R.id.sector);
				holder.status = (TextView) convertView
						.findViewById(R.id.status);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.flight.setText(arrives.get(position).getFlight());
			holder.time.setText(arrives.get(position).getTime());
			holder.sector.setText(arrives.get(position).getSector());
			holder.status.setText(arrives.get(position).getStatus());
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView flight;
		public TextView time;
		public TextView sector;
		public TextView status;
	}

	private static class ArriveScheduleLoader
			extends
			ProgressAsyncTask<String, Void, List<Arrive>, ScheduleArriveActivity> {

		public ArriveScheduleLoader(ScheduleArriveActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);

		}

		@Override
		protected List<Arrive> doInBackground(ScheduleArriveActivity target,
				String... params) {
			List<Arrive> arriveSchedule = null;
			try {
				HtmlHelper arrive = new HtmlHelper(params[0]);
				arriveSchedule = arrive.getArriveSchedule();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return arriveSchedule;
		}

		@Override
		protected void onPostExecute(ScheduleArriveActivity target,
				List<Arrive> result) {
			super.onPostExecute(target, result);
			if (result == null) {
				Toast.makeText(target, "Не удалось загрузить расписание",
						Toast.LENGTH_SHORT).show();
				return;
			}
			ArriveAdapter adapter = target.new ArriveAdapter(result);
			target.setListAdapter(adapter);
		}
	}
}
