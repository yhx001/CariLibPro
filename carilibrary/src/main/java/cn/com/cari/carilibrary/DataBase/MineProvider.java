package cn.com.cari.carilibrary.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MineProvider extends ContentProvider {
	private SQLiteDatabase sqlDB;
	private DBHelper dbHelper;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		int flag;
		sqlDB = dbHelper.getWritableDatabase();
		flag = sqlDB.delete(args.table, selection, selectionArgs);
		return flag;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SqlArguments args = new SqlArguments(uri);
		sqlDB = dbHelper.getWritableDatabase(); // called every time you need
												// write to database
		long rowId = sqlDB.insert(args.table, "", values);
		if (rowId > 0) {
			Uri rowUri = ContentUris.appendId(uri.buildUpon(), rowId).build();
			getContext().getContentResolver().notifyChange(rowUri, null);
			return rowUri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		System.out.println("provider onCreate");
		dbHelper = new DBHelper(getContext());
		return (dbHelper == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		qb.setTables(args.table);
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
		sqlDB = dbHelper.getWritableDatabase();
		return sqlDB.update(args.table, values, selection, selectionArgs);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int result = 0;
		SqlArguments args = new SqlArguments(uri);
		int numValues = values.length;
		sqlDB = dbHelper.getWritableDatabase();
		sqlDB.beginTransaction();

		try {
			for (int i = 0; i < numValues; i++) {
				if (sqlDB.insert(args.table, "", values[i]) > 0) {
					result++;
				} else {
					return 0;
				}
				sqlDB.yieldIfContendedSafely(); // Temporarily end the
												// transaction
												// to let other threads run
			}
			sqlDB.setTransactionSuccessful();
		} finally {
			sqlDB.endTransaction();
		}

		return result;
	}

	static class SqlArguments {
		public final String table;
		public final String where;
		public final String[] args;

		SqlArguments(Uri url, String where, String[] args) {
			if (url.getPathSegments().size() == 1) {
				this.table = url.getPathSegments().get(0);
				this.where = where;
				this.args = args;
			} else if (url.getPathSegments().size() != 2) {
				throw new IllegalArgumentException("Invalid URI: " + url);
			} else if (!TextUtils.isEmpty(where)) {
				throw new UnsupportedOperationException("WHERE clause not supported: " + url);
			} else {
				this.table = url.getPathSegments().get(0);
				this.where = "_id=" + ContentUris.parseId(url);
				this.args = null;
			}
		}

		SqlArguments(Uri url) {
			if (url.getPathSegments().size() == 1) {
				table = url.getPathSegments().get(0);
				where = null;
				args = null;
			} else {
				throw new IllegalArgumentException("Invalid URI: " + url);
			}
		}
	}
}
