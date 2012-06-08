package by.airoports.provider;

import java.util.ArrayList;
import java.util.Map;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DbProvider extends ContentProvider {
	private DBHelper dbHelper;
	static final String PARAMETER_NOTIFY = "notify";

	@Override
	public boolean onCreate() {
		final Context context = getContext();
		dbHelper = new DBHelper(context);
		return true;
	}

	@Override
	public String getType(Uri uri) {
		SqlArguments args = new SqlArguments(uri);
		return args.getType();
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SqlArguments args = new SqlArguments(uri);

		final SelectionBuilder selectionBuilder = args.getWhereClause();
		selection = selectionBuilder.getSelection(selection);
		selectionArgs = selectionBuilder.getSelectionArgs(selectionArgs);

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(args.getTableName());
		Map<String, String> columnMap = args.getProjectionMap();
		if (columnMap != null && !columnMap.isEmpty()) {
			qb.setProjectionMap(columnMap);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor result = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		result.setNotificationUri(getContext().getContentResolver(), uri);

		return result;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SqlArguments args = new SqlArguments(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		long rowId = db.replaceOrThrow(args.getTableName(), null, values);
		if (rowId > 0) {
			uri = ContentUris.withAppendedId(uri, rowId);
			args.notifyChange(getContext(), uri);
			return uri;
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count = 0;
		if (values.size() > 0) {
			final SelectionBuilder selectionBuilder = args.getWhereClause();
			selection = selectionBuilder.getSelection(selection);
			selectionArgs = selectionBuilder.getSelectionArgs(selectionArgs);
			count = db.update(args.getTableName(), values, selection, selectionArgs);
		}
		if (count > 0) {
			args.notifyChange(getContext(), uri);
		}
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count = 0;

		final SelectionBuilder selectionBuilder = args.getWhereClause();
		selection = selectionBuilder.getSelection(selection);
		selectionArgs = selectionBuilder.getSelectionArgs(selectionArgs);
		count = db.delete(args.getTableName(), selection, selectionArgs);
		if (count > 0) {
			args.notifyChange(getContext(), uri);
		}
		return count;
	}

	@Override
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
			throws OperationApplicationException {
		final SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			final int numOperations = operations.size();
			final ContentProviderResult[] results = new ContentProviderResult[numOperations];
			for (int i = 0; i < numOperations; i++) {
				results[i] = operations.get(i).apply(this, results, i);
			}
			db.setTransactionSuccessful();
			return results;
		} finally {
			db.endTransaction();
		}
	}

	private static class SqlArguments {
		private static final int SAFE_ITEM = 101;
		private static final int SAFE_ITEM_ID = 102;

		private static final int SAFE_FILE = 201;
		private static final int SAFE_FILE_ID = 202;

		private static final int SAFE_CARD = 301;
		private static final int SAFE_CARD_ID = 302;

		private static final int SAFE_TEMPLATE = 401;
		private static final int SAFE_TEMPATE_ID = 402;

		private static final UriMatcher URI_MATCHER;
		static {
			URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
			// URI_MATCHER.addURI(Constants.AUTHORITY, "safe_item", SAFE_ITEM);
			// URI_MATCHER.addURI(Constants.AUTHORITY, "safe_item/#", SAFE_ITEM_ID);
		}

		private final Uri uri;
		private final int match;

		SqlArguments(Uri uri) {
			this.uri = uri;
			match = URI_MATCHER.match(uri);
		}

		String getType() {
			switch (match) {
			// case SAFE_ITEM:
			// return SafeItemContract.CONTENT_TYPE_LIST;
			// case SAFE_ITEM_ID:
			// return SafeItemContract.CONTENT_TYPE_ITEM;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
			}
		}

		Map<String, String> getProjectionMap() {
			switch (match) {
			// case SAFE_ITEM:
			// case SAFE_ITEM_ID:
			// return SafeItemContract.PROJECTION_MAP;

			}
			return null;
		}

		String getTableName() {
			switch (match) {
			// case SAFE_ITEM:
			// case SAFE_ITEM_ID:
			// return SafeItemContract.TABLE_NAME;
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
			}
		}

		SelectionBuilder getWhereClause() {
			SelectionBuilder sb = new SelectionBuilder();
			switch (match) {
			case SAFE_ITEM_ID:
				// sb.appendWhere(SafeItemContract.Qualified._ID, "=", ContentUris.parseId(uri));
				break;
			}
			return sb;
		}

		private void notifyChange(Context context, Uri uri) {
			String notify = uri.getQueryParameter(PARAMETER_NOTIFY);
			if (notify != null && "false".equals(notify)) {
				return;
			}
			final ContentResolver resolver = context.getContentResolver();
			switch (match) {
			case SAFE_ITEM_ID:
				// resolver.notifyChange(SafeItemContract.CONTENT_URI, null);
				break;
			default:
				break;
			}
			resolver.notifyChange(uri, null);
		}
	}

	private static class SelectionBuilder {
		private final StringBuilder clause = new StringBuilder();
		private final ArrayList<String> args = new ArrayList<String>();

		/**
		 * Append the given selection clause to the internal state. Each clause is surrounded with parenthesis and
		 * combined using {@code AND}.
		 */
		SelectionBuilder where(String selection, String... selectionArgs) {
			if (TextUtils.isEmpty(selection)) {
				if (selectionArgs != null && selectionArgs.length > 0) {
					throw new IllegalArgumentException("Valid selection required when including arguments");
				}
				return this;
			}
			if (clause.length() > 0) {
				clause.append(" AND ");
			}
			clause.append("(").append(selection).append(")");
			if (selectionArgs != null) {
				for (String arg : selectionArgs) {
					args.add(arg);
				}
			}
			return this;
		}

		SelectionBuilder appendWhere(String name, String operation, String value) {
			return where(name + " " + operation + " ?", value);
		}

		SelectionBuilder appendWhere(String name, String operation, long value) {
			return appendWhere(name, operation, String.valueOf(value));
		}

		/**
		 * Return selection string for current internal state.
		 */
		String getSelection(String where) {
			if (where != null) {
				where(where);
			}
			return clause.toString();
		}

		/**
		 * Return selection arguments for current internal state.
		 */
		String[] getSelectionArgs(String[] selectionArgs) {
			if (selectionArgs != null) {
				for (String arg : selectionArgs) {
					args.add(arg);
				}
			}
			return args.toArray(new String[args.size()]);
		}
	}
}
