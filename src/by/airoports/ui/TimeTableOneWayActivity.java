package by.airoports.ui;

import java.io.IOException;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import by.airoports.R;
import by.airoports.item.TimeTableInfo;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

import com.google.common.collect.Lists;

public class TimeTableOneWayActivity extends ExpandableListActivity {

	ExpandableListAdapter mAdapter;

	private boolean goTo = true;

	public static Intent buildIntent(Context context, String url, String airoportName) {
		Intent intent = new Intent(context, TimeTableOneWayActivity.class);
		intent.putExtra(SearchTimeTableActivity.TIME_TABLE_URL, url);
		intent.putExtra(SearchTimeTableActivity.TIME_TABLE_AIROPORT_NAME, airoportName);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.timetable_action_bar, getExpandableListView(), false);
		LinearLayout layout = (LinearLayout) header.findViewById(R.id.tab_To);
		layout.setPressed(true);
		TextView airoportTo = (TextView) header.findViewById(R.id.airoportNameTo);		
		getExpandableListView().addHeaderView(header, null, false);		
		String url = getIntent().getStringExtra(
				SearchTimeTableActivity.TIME_TABLE_URL);
		String airoportName = getIntent().getStringExtra(
				SearchTimeTableActivity.TIME_TABLE_AIROPORT_NAME);
		airoportTo.setText(airoportName);
		TimeTableLoader loader = new TimeTableLoader(this,
				new ProgressDialogInfo("LOADER",
						getString(R.string.load_schedule), true, false));
		loader.execute(url);
	}

	public void goTo(View v) {
		String url = getIntent().getStringExtra(
				SearchTimeTableActivity.TIME_TABLE_URL);
		goTo = true;
		TimeTableLoader loader = new TimeTableLoader(this,
				new ProgressDialogInfo("LOADER",
						getString(R.string.load_schedule), true, false));
		loader.execute(url);
	}

	public void goBack(View v) {

		String url = getIntent().getStringExtra(
				SearchTimeTableActivity.TIME_TABLE_URL);
		goTo = false;
		TimeTableLoader loader = new TimeTableLoader(this,
				new ProgressDialogInfo("LOADER",
						getString(R.string.load_schedule), true, false));
		loader.execute(url);
	}

	/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
	 * photo is displayed as an image. This adapter supports clearing the list
	 * of photos and adding a new photo.
	 * 
	 */
	public class ExpandableListAdapter extends BaseExpandableListAdapter {
		// Sample data set. children[i] contains the children (String[]) for
		// groups[i].
		private final List<boolean[]> weekSchedule;
		private final List<String[]> weekDescription;

		private String[] titles = { "Отправление", "Прибытие", "В самолете",
				"Самолет", "Рейс", "Выполняется" };

		public ExpandableListAdapter(final List<boolean[]> weekSchedule,
				final List<String[]> description) {
			// List<boolean[]> list = Lists.newArrayList();
			// List<String[]> list2 = Lists.newArrayList();
			// boolean[] t = new boolean[7];
			// t[0] = true;
			// t[1] = true;
			// t[2] = false;
			// t[3] = false;
			// t[4] = true;
			// t[5] = false;
			// list.add(t);

			this.weekSchedule = weekSchedule;
			weekDescription = description;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return weekDescription.get(groupPosition)[childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return weekDescription.get(groupPosition).length;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = new TextView(TimeTableOneWayActivity.this);
			textView.setText(titles[childPosition] + " "
					+ getChild(groupPosition, childPosition).toString());
			return textView;
		}

		public Object getGroup(int groupPosition) {
			return weekSchedule.get(groupPosition);
		}

		public int getGroupCount() {
			return weekSchedule.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			boolean[] flights = weekSchedule.get(groupPosition);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(15, 0, 10, 0); // TODO calculate correct image size fo
			// TODO set empty image
			ImageView image = new ImageView(TimeTableOneWayActivity.this);
			image.setLayoutParams(lp);
			setImageResource(flights[0], image);
			ImageView image2 = new ImageView(TimeTableOneWayActivity.this);
			image2.setLayoutParams(lp);
			setImageResource(flights[1], image2);
			ImageView image3 = new ImageView(TimeTableOneWayActivity.this);
			image3.setLayoutParams(lp);
			setImageResource(flights[2], image3);
			ImageView image4 = new ImageView(TimeTableOneWayActivity.this);
			image4.setLayoutParams(lp);
			setImageResource(flights[3], image4);

			ImageView image5 = new ImageView(TimeTableOneWayActivity.this);
			image5.setLayoutParams(lp);
			setImageResource(flights[4], image5);

			ImageView image6 = new ImageView(TimeTableOneWayActivity.this);
			setImageResource(flights[5], image6);

			ImageView image7 = new ImageView(TimeTableOneWayActivity.this);
			image7.setLayoutParams(lp);
			setImageResource(flights[6], image7);
			// Center the text vertically
			// Set the text starting position
			image.setPadding(70, 0, 0, 0);
			LinearLayout layout = new LinearLayout(TimeTableOneWayActivity.this);
			layout.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 50));
			layout.setGravity(Gravity.CENTER | Gravity.LEFT);
			layout.addView(image);
			layout.addView(image2);
			layout.addView(image3);
			layout.addView(image4);
			layout.addView(image5);
			layout.addView(image6);
			layout.addView(image7);

			return layout;
		}

		private void setImageResource(boolean flight, ImageView image) {
			if (flight) {
				image.setImageResource(R.drawable.airplane);
			} else {
				image.setMinimumWidth(25);
			}
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}
	}

	private static class TimeTableLoader extends
			ProgressAsyncTask<String, Void, TimeTableInfo, TimeTableOneWayActivity> {

		public TimeTableLoader(TimeTableOneWayActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);
		}

		@Override
		protected TimeTableInfo doInBackground(TimeTableOneWayActivity target,
				String... params) {
			TimeTableInfo tableInfo;
			try {
				HtmlHelper timeTable = new HtmlHelper(params[0]);
				List<boolean[]> saveWeekSchedule = null;
				List<String[]> saveWeekDescription = null;				
				if (target.goTo) {
					saveWeekSchedule = timeTable.saveWeekScheduleTo();
					saveWeekDescription = timeTable.saveWeekDescriptionTo();
				} else {
					saveWeekSchedule = Lists.newArrayList();
					saveWeekDescription = Lists.newArrayList();
				}
				if (saveWeekDescription.size() != saveWeekDescription.size()) {
					throw new IllegalArgumentException("wrong parse");
				}
				tableInfo = new TimeTableInfo(saveWeekSchedule,
						saveWeekDescription);
			} catch (IOException e) {
				e.printStackTrace();
				tableInfo = null;
			}

			return tableInfo;
		}

		@Override
		protected void onPostExecute(TimeTableOneWayActivity target,
				TimeTableInfo result) {
			super.onPostExecute(target, result);
			if (result != null) {
				Toast.makeText(target, "Загрузка расписания прошла успешно",
						Toast.LENGTH_SHORT).show();
				List<boolean[]> schedule = result.getSaveWeekSchedule();
				List<String[]> weekDescription = result
						.getSaveWeekDescription();
				ExpandableListAdapter adapter = target.new ExpandableListAdapter(
						schedule, weekDescription);
				target.setListAdapter(adapter);
				LinearLayout layout = null;
				if (target.goTo) {
					layout = (LinearLayout) target.findViewById(R.id.tab_To);
					layout.setPressed(true);
				} else {
					layout = (LinearLayout) target.findViewById(R.id.tab_From);
					layout.setPressed(true);
				}
			}
		}
	}

}