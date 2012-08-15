package by.airoports.ui;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import by.airoports.R;
import by.airoports.item.Departure;
import by.airoports.item.DepartureDetails;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

// TODO create correct classes
public class ScheduleDeparturesActivity extends ListActivity {

	public static Intent buildIntent(Context context) {
		Intent intent = new Intent(context, ScheduleDeparturesActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_departure);
		DeparturesScheduleLoader scheduleLoader = new DeparturesScheduleLoader(
				this, new ProgressDialogInfo("LOADER", "Load a schedule", true,
						false));
		scheduleLoader.execute("http://www.airport.by/timetable/online");
	}

	public void onDeparturesClick(View v) {

	}

	public void onArriveClick(View v) {
		startActivity(ScheduleArriveActivity.buildIntent(this));
		finish();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, DepartureDetailsActivity.class);

		DeparturesAdapter adapter = (DeparturesAdapter) getListAdapter();
		Departure item = adapter.getItem(position);
		DepartureDetails details = new DepartureDetails(item);
		intent.putExtra(DepartureDetails.class.getSimpleName(), details);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

	private class DeparturesAdapter extends BaseAdapter {

		private final List<Departure> departures;
		private final LayoutInflater inflater;

		public DeparturesAdapter(List<Departure> departures) {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.departures = departures;
		}

		@Override
		public int getCount() {
			return departures.size();
		}

		@Override
		public Departure getItem(int position) {
			return departures.get(position);
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
						R.layout.list_item_departure_schedule, null);
				holder = new ViewHolder();
				holder.flight = (TextView) convertView
						.findViewById(R.id.flight);
				holder.departureTime = (TextView) convertView
						.findViewById(R.id.departureTime);
				holder.destination = (TextView) convertView
						.findViewById(R.id.destination);
				holder.sector = (TextView) convertView
						.findViewById(R.id.sector);
				holder.status = (TextView) convertView
						.findViewById(R.id.status);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Departure item = getItem(position);
			holder.flight.setText(item.getFlight());
			holder.departureTime.setText(item.getTime());
			holder.destination.setText(item.getDestination());
			holder.sector.setText(item.getSector());
			holder.status.setText(item.getStatus());
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView flight;
		public TextView departureTime;
		public TextView destination;
		public TextView sector;
		public TextView status;
	}

	private static class DeparturesScheduleLoader
			extends
			ProgressAsyncTask<String, Void, List<Departure>, ScheduleDeparturesActivity> {

		public DeparturesScheduleLoader(ScheduleDeparturesActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);

		}

		@Override
		protected List<Departure> doInBackground(
				ScheduleDeparturesActivity target, String... params) {
			List<Departure> departureSchedule = null;
			try {
				HtmlHelper departureData = new HtmlHelper(params[0]);
				departureSchedule = departureData.getDeparturesSchedule();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return departureSchedule;
		}

		@Override
		protected void onPostExecute(ScheduleDeparturesActivity target,
				List<Departure> result) {
			super.onPostExecute(target, result);
			DeparturesAdapter adapter = target.new DeparturesAdapter(result);
			target.setListAdapter(adapter);
			Toast.makeText(target, "Загрузка расписания прошла успешно",
					Toast.LENGTH_SHORT).show();
		}
	}
}
