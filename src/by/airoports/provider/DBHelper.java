package by.airoports.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import by.airoports.app.Constants;
import by.airoports.contract.AiroportContract;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		onUpgrade(db, 0, Constants.DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 execSQL(db, AiroportContract.DATABASE_CREATE);
	}

	private void execSQL(final SQLiteDatabase db, final String sql) {
		if (sql == null) {
			return;
		}
		if (sql.contains("//")) {
			for (String command : sql.split("//")) {
				if (TextUtils.isEmpty(command)) {
					continue;
				}
				db.execSQL(command);
			}
		} else {
			db.execSQL(sql);
		}
	}
}

