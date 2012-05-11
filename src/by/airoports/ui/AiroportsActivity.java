package by.airoports.ui;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AiroportsActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airoports);
		AiroportsAdapter adapter = new AiroportsAdapter();
		setListAdapter(adapter);
	}

	private class AiroportsAdapter extends BaseAdapter {

		private final String[] airoportsData = { "Belavia", "Minsk1" };
		private final LayoutInflater inflater;

		public AiroportsAdapter() {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return airoportsData.length;
		}

		@Override
		public Object getItem(int position) {
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
				convertView = inflater.inflate(R.layout.list_item_airoports, null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView.findViewById(R.id.airoportName);
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
}
