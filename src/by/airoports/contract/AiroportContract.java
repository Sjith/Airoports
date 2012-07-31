package by.airoports.contract;

import by.airoports.app.Constants;
import android.content.ContentResolver;
import android.net.Uri;

public class AiroportContract {
	public static final String TABLE_AIROPORTS = "airoports";

	public interface COLUMNS {
		String _ID = "_id";
		String NAME = "name";
	}


	public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY
			+ "/" + Constants.BASE_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/airoports";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/airoport";

	// Database creation sql statement
	public static final String DATABASE_CREATE = "create table "
			+ TABLE_AIROPORTS + "(" + COLUMNS._ID
			+ " integer primary key autoincrement, " + COLUMNS.NAME
			+ " text not null);";
}
