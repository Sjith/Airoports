package by.airoports.contract;

import android.database.Cursor;

public class Airoports {
	private long id;
	private String name;


	public Airoports(Cursor cursor) {
		super();
		this.id = cursor.getLong(cursor
				.getColumnIndexOrThrow(AiroportContract.COLUMNS._ID));
		this.name = cursor.getString(cursor
				.getColumnIndexOrThrow(AiroportContract.COLUMNS.NAME));			
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name;
	}
}
