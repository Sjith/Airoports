package by.airoports.contract;

import android.content.ContentResolver;
import android.net.Uri;
import by.airoports.app.Constants;

public class AiroportContract {
	public static final String TABLE_AIROPORTS = "airoports";

	public interface COLUMNS {
		String _ID = "_id";
		String NAME = "name";
	}

	public interface DATA {
		String _ID = "_id";
		String NAME = "name";		
	}

	public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY
			+ "/" + TABLE_AIROPORTS);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/" + TABLE_AIROPORTS;

	// TODO create correct projection
	public static final String[] PROJECTION = new String[] { AiroportContract.DATA._ID,
		AiroportContract.DATA.NAME };

	// Database creation sql statement

	public static final String createTable() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("create table ").append(TABLE_AIROPORTS)
				.append("(").append(COLUMNS._ID)
				.append(" integer primary key autoincrement,")
				.append(COLUMNS.NAME).append(" text,").append(" integer").append(");");		
		return sqlBuilder.toString();
	}
}
