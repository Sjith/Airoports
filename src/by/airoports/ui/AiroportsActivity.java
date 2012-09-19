package by.airoports.ui;

import java.io.IOException;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import by.airoports.R;
import by.airoports.util.HtmlHelper;
import static by.airoports.app.Constants.TAG;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

/**
 * Show list of belavia airports in real time
 */
public class AiroportsActivity extends ListActivity {

	public static Intent buildIntent(Context context) {
		return new Intent(context, AiroportsActivity.class);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_airoports);
		AiroportsLoader airoportsLoader = new AiroportsLoader(
				this, new ProgressDialogInfo("LOADER", "Load an airoports list", true,
						false));
		airoportsLoader.execute("http://belavia.by/table/");
	}

	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent data = new Intent();
		AiroportsAdapter adapter = (AiroportsAdapter) getListAdapter();
		String item = adapter.getItem(position);
		data.putExtra(SearchFlightActivity.AIROPORT_NAME,item);	
		setResult(SearchFlightActivity.AIROPORT_SELECT, data);
		finish();
		super.onListItemClick(l, v, position, id);
	}
	private class AiroportsAdapter extends BaseAdapter {

		private final String[] airoportsData;
		private final LayoutInflater inflater;

		public AiroportsAdapter(String[] airoportsData) {
			this.airoportsData = airoportsData;
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return airoportsData.length;
		}

		@Override
		public String getItem(int position) {
			return airoportsData[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item_airoports,
						null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.airoportName);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textView.setText(airoportsData[position]);
			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView textView;
	}

	private static class AiroportsLoader extends
			ProgressAsyncTask<String, Void, List<String>, AiroportsActivity> {

		public AiroportsLoader(AiroportsActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);

		}

		@Override
		protected List<String> doInBackground(AiroportsActivity target,
				String... params) {
			List<String> saveAiroports;
			try {
				HtmlHelper html = new HtmlHelper();
				saveAiroports = html.saveAiroports(params[0]);
			} catch (IOException e) {
				saveAiroports = null;
				e.printStackTrace();
			}
			return saveAiroports;
		}

		@Override
		protected void onPostExecute(AiroportsActivity target,
				List<String> result) {
			super.onPostExecute(target, result);
			if (result == null) {				
				Toast.makeText(target, "Не удалось загрузить расписание",
						Toast.LENGTH_SHORT).show();
				return;
			}			
			AiroportsAdapter adapter = target.new AiroportsAdapter(
					result.toArray(new String[result.size()]));
			target.setListAdapter(adapter);
		}
	}
}
