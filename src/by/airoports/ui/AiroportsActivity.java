package by.airoports.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static by.airoports.app.Constants.TAG;
import by.airoports.R;
import by.airoports.item.Airoport;
import by.airoports.item.AiroportItem;
import by.airoports.item.AiroportSection;
import by.airoports.util.HtmlHelper;
import by.airoports.util.ProgressAsyncTask;
import by.airoports.util.ProgressAsyncTask.ProgressDialogInfo;

import com.google.common.collect.Lists;

/**
 * Show list of belavia airports in real time
 */
public class AiroportsActivity extends ListActivity {

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			AiroportsAdapter listAdapter = (AiroportsAdapter) getListView()
					.getAdapter();
			listAdapter.getFilter().filter(s);
		}

	};

	public static Intent buildIntent(Context context) {
		return new Intent(context, AiroportsActivity.class);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airoports);		
			
		TextView searchText = (TextView)findViewById(R.id.search);
		searchText.addTextChangedListener(filterTextWatcher);
		AiroportsLoader airoportsLoader = new AiroportsLoader(this,
				new ProgressDialogInfo("LOADER", "Load an airoports list",
						true, false));
		airoportsLoader.execute("http://belavia.by/table/");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent data = new Intent();
		AiroportsAdapter adapter = (AiroportsAdapter) getListAdapter();
		AiroportItem item = adapter.getItem(position);
		if (!item.isSectionItem()) {
			Airoport airoport = (Airoport) item;
			data.putExtra(SearchFlightActivity.AIROPORT_NAME,
					airoport.getName());
			setResult(SearchFlightActivity.AIROPORT_SELECT, data);
			finish();
		}
		super.onListItemClick(l, v, position, id);
	}

	private class AiroportsAdapter extends ArrayAdapter<AiroportItem> {

		private final List<AiroportItem> airoportsData;
		private final List<AiroportItem> original;
		private final LayoutInflater inflater;
		private ViewHolderSectionName holderSection;
		private ViewHolderName holderName;
		private SearchFilter filter;

		public AiroportsAdapter(Activity context,
				List<AiroportItem> airoportsData) {
			super(context, 0, airoportsData);

			this.airoportsData = airoportsData;
			this.original = Lists.newArrayList(airoportsData);
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return airoportsData.size();
		}

		@Override
		public AiroportItem getItem(int position) {
			return airoportsData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AiroportItem airoportItem = airoportsData.get(position);

			if (airoportItem.isSectionItem()) {
				AiroportSection sectionItem = (AiroportSection) airoportItem;

				if (convertView == null
						|| !convertView.getTag().equals(holderSection)) {
					convertView = inflater.inflate(R.layout.alphabet_separator,
							null);

					holderSection = new ViewHolderSectionName();
					convertView.setTag(holderSection);
				} else {
					holderSection = (ViewHolderSectionName) convertView
							.getTag();
				}

				holderSection.section = (TextView) convertView
						.findViewById(R.id.alphabet_letter);
				holderSection.section.setText(String.valueOf(sectionItem
						.getSectionLetter()));

			} else {
				Airoport ei = (Airoport) airoportItem;

				if (convertView == null
						|| !convertView.getTag().equals(holderName)) {
					convertView = inflater.inflate(
							R.layout.list_item_airoports, null);

					holderName = new ViewHolderName();
					convertView.setTag(holderName);
				} else {
					holderName = (ViewHolderName) convertView.getTag();
				}

				holderName.name = (TextView) convertView
						.findViewById(R.id.airoportName);

				if (holderName.name != null) {
					holderName.name.setText(ei.getName());
				}
			}
			return convertView;
		}

		@Override
		public Filter getFilter() {
			if (filter == null)
				filter = new SearchFilter();
			return filter;
		}

		private class SearchFilter extends Filter {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// NOTE: this function is *always* called from a background
				// thread, and
				// not the UI thread.
				constraint = constraint.toString().toLowerCase();
				FilterResults result = new FilterResults();
				// update filter charsequence;
				if (constraint != null && constraint.toString().length() > 0) {
					List<Airoport> founded = Lists.newArrayList();
					for (AiroportItem item : airoportsData) {
						if (!item.isSectionItem()) {
							Airoport airoport = (Airoport) item;
							if (airoport.getName().toLowerCase()
									.contains(constraint))
								;
							founded.add(airoport);
						}
					}
					result.values = founded;
					result.count = founded.size();
				} else {
					synchronized (this) {
						result.values = original;
						result.count = original.size();
					}
				}
				return result;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				clear();
				for (AiroportItem o : (List<AiroportItem>) results.values) {
					add(o);
				}
				notifyDataSetChanged();
			}
		}
	}

	public static class ViewHolderName {
		public TextView name;
	}

	public static class ViewHolderSectionName {
		public TextView section;
	}

	private static class AiroportsLoader
			extends
			ProgressAsyncTask<String, Void, List<AiroportItem>, AiroportsActivity> {

		public AiroportsLoader(AiroportsActivity target,
				by.airoports.util.ProgressAsyncTask.ProgressDialogInfo info) {
			super(target, info);

		}

		@Override
		protected List<AiroportItem> doInBackground(AiroportsActivity target,
				String... params) {
			List<AiroportItem> saveAiroports;
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
				List<AiroportItem> result) {
			super.onPostExecute(target, result);
			if (result == null) {
				Toast.makeText(target, "Не удалось загрузить расписание",
						Toast.LENGTH_SHORT).show();
				return;
			}
			List<Airoport> list = Arrays.asList(result
					.toArray(new Airoport[result.size()]));
			setAdapterToListview(target, list);
		}

		private void setAdapterToListview(AiroportsActivity target,
				List<Airoport> result) {

			List<AiroportItem> itemsSection = Lists.newArrayList();

			if (null != result && !result.isEmpty()) {

				Collections.sort(result);

				char checkChar = ' ';

				for (int index = 0; index < result.size(); index++) {

					Airoport objItem = (Airoport) result.get(index);
					char firstChar = objItem.getName().charAt(0);

					if (' ' != checkChar) {
						if (checkChar != firstChar) {
							AiroportSection objSectionItem = new AiroportSection(
									firstChar);
							itemsSection.add(objSectionItem);
						}
					} else {
						AiroportSection objSectionItem = new AiroportSection(
								firstChar);
						itemsSection.add(objSectionItem);
					}

					checkChar = firstChar;

					itemsSection.add(objItem);
				}
			} else {
				//
			}

			AiroportsAdapter adapter = target.new AiroportsAdapter(target,
					itemsSection);
			target.setListAdapter(adapter);
		}
	}
}
