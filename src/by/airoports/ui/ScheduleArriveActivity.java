package by.airoports.ui;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

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
import by.airoports.item.Arrive;
import by.airoports.item.ArriveDetails;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

public class ScheduleArriveActivity extends ListActivity {

	public static Intent buildIntent(Context context, String url,
			String airoportName, String date) {
		Intent intent = new Intent(context, ScheduleArriveActivity.class);
		intent.putExtra(SearchFlightActivity.SCHEDULE_URL, url);
		intent.putExtra(SearchFlightActivity.AIROPORT_NAME, airoportName);
		intent.putExtra(SearchFlightActivity.DATE, date);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_arrive);
		Intent intent = getIntent();
		String url = intent.getStringExtra(SearchFlightActivity.SCHEDULE_URL);
		String airoport = intent
				.getStringExtra(SearchFlightActivity.AIROPORT_NAME);
		String date = intent.getStringExtra(SearchFlightActivity.DATE);		

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.arrive_action_bar, getListView(), false);
		getListView().addHeaderView(header, null, false);		
		TextView airoportName = (TextView) header
				.findViewById(R.id.airoportName);
		airoportName.setText(airoport);
		TextView departureDate = (TextView) header
				.findViewById(R.id.arriveDate);
		departureDate.setText(date);
		ArriveScheduleLoader scheduleLoader = new ArriveScheduleLoader(
				this,
				new ProgressDialogInfo("LOADER", "Load a schedule", true, false));
		scheduleLoader.execute(url);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ArriveDetailsActivity.class);

		ArriveAdapter adapter = (ArriveAdapter) getListAdapter();
		Arrive item = adapter.getItem(position);
		String date = getIntent().getStringExtra(SearchFlightActivity.DATE);
		ArriveDetails details = new ArriveDetails(item,date);
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
				holder.flightFrom = (TextView) convertView
						.findViewById(R.id.flightFrom);
				holder.status = (TextView) convertView
						.findViewById(R.id.status);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.flight.setText(arrives.get(position).getFlight());
			holder.flightFrom.setText(arrives.get(position).getFlightFrom());
			holder.time.setText(arrives.get(position).getTime());			
			holder.status.setText(arrives.get(position).getStatus());
			return convertView;
		}

	}

	public static class ViewHolder {		
		public TextView flight;
		public TextView flightFrom;
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
				HtmlHelper arrive = new HtmlHelper();
				arriveSchedule = arrive.saveArrive(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {		
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
			if (result.isEmpty()) {
				target.getListView().setEmptyView(target.findViewById(R.layout.empty_view));
				return;
			}
			ArriveAdapter adapter = target.new ArriveAdapter(result);
			target.setListAdapter(adapter);
		}
	}
}
