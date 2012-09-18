package by.airoports.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CursorAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import by.airoports.R;
import by.airoports.contract.Airoports;

public class AiroportsActivityIndexer extends ListActivity {

	public static Intent buildIntent(Context context) {
		return new Intent(context, AiroportsActivityIndexer.class);
	}
	
	private final String[] airoportsData = { "Airoport1", "Belavia",
	"Minsk1" };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airoports);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	private class AiroportsAdapter extends CursorAdapter implements SectionIndexer {

		private final Cursor cursor;
		private AlphabetIndexer indexer;

		//this array is for fast lookup later and will contain the just the
		//alphabet sections that actually appear in the data set
		private int[] usedSectionIndicies;
		  private int[] usedSectionNumbers;
		//map from alphabet section to the index it ought
		//to appear in
		private Map<Integer, Integer> sectionToPosition;

		//map from alphabet section to the number of other sections
		//that appear before it
		private Map<Integer, Integer> sectionToOffset;
		
		public AiroportsAdapter(Context context, Cursor c) {
			super(context, c);
			cursor = c;
		}

		@Override
		public int getCount() {
			return cursor.getCount();
		}

		@Override
		public Airoports getItem(int position) {
			cursor.moveToPosition(position);
			return new Airoports(cursor);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.list_item_airoports, null);
			return view;

		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			Airoports airoports = new Airoports(cursor);
			TextView textView = (TextView) view.findViewById(R.id.airoportName);
			textView.setText(airoports.getName());
		}

		@Override
		public Object[] getSections() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getPositionForSection(int section) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
