package by.airoports.provider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import by.airoports.app.Constants;
import by.airoports.contract.AiroportContract;

public class DBProvider extends ContentProvider {

	private DBHelper database;

	// Used for the UriMacher
	private static final int AIROPORTS = 10;
	private static final int AIROPORTS_ID = 20;
	public static final Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY
			+ "/" + Constants.BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/airoports";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/airoports";

	private static final UriMatcher URIMATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		URIMATCHER.addURI(Constants.AUTHORITY, AiroportContract.TABLE_AIROPORTS,
				AIROPORTS);
		URIMATCHER.addURI(Constants.AUTHORITY,AiroportContract. TABLE_AIROPORTS + "/#",
				AIROPORTS_ID);
	}

	@Override
	public boolean onCreate() {
		database = new DBHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
	

		int uriType = URIMATCHER.match(uri);
		switch (uriType) {
		case AIROPORTS:
			queryBuilder.setTables(AiroportContract.TABLE_AIROPORTS);
			break;
		case AIROPORTS_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(AiroportContract.COLUMNS._ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = URIMATCHER.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case AIROPORTS:
			id = sqlDB.insert(AiroportContract.TABLE_AIROPORTS, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(Constants.BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = URIMATCHER.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case AIROPORTS:
			rowsDeleted = sqlDB.delete(AiroportContract.TABLE_AIROPORTS,
					selection, selectionArgs);
			break;
		case AIROPORTS_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(AiroportContract.TABLE_AIROPORTS,
						AiroportContract.COLUMNS._ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(AiroportContract.TABLE_AIROPORTS,
						AiroportContract.COLUMNS._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = URIMATCHER.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case AIROPORTS:
			rowsUpdated = sqlDB.update(AiroportContract.TABLE_AIROPORTS,
					values, selection, selectionArgs);
			break;
		case AIROPORTS_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(AiroportContract.TABLE_AIROPORTS,
						values, AiroportContract.COLUMNS._ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(AiroportContract.TABLE_AIROPORTS,
						values, AiroportContract.COLUMNS._ID + "=" + id + " and "
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = { AiroportContract.COLUMNS.NAME,
				AiroportContract.COLUMNS._ID };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}

}
