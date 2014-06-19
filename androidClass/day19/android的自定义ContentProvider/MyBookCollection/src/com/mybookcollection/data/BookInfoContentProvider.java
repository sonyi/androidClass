package com.mybookcollection.data;

import com.mybookcollection.provider.MetaDataContract;
import com.mybookcollection.provider.MetaDataContract.BookContract;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BookInfoContentProvider extends ContentProvider {
	private UriMatcher mUriMatcher;
	private SqliteHelper mSqliteHelper;

	private static final int TYPE_DIR = 0;
	private static final int TYPE_ITEM = 1;

	@Override
	public boolean onCreate() {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(MetaDataContract.AUTHORITY, "book", TYPE_DIR);
		mUriMatcher.addURI(MetaDataContract.AUTHORITY, "book/#", TYPE_ITEM);

		mSqliteHelper = new SqliteHelper(getContext());

		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
		case TYPE_DIR:
			return BookContract.CONTENT_TYPE;
		case TYPE_ITEM:
			return BookContract.ENTRY_CONTENT_TYPE;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String tableName = uri.getPathSegments().get(0);

		SQLiteDatabase db = mSqliteHelper.getReadableDatabase();
		Cursor cursor = null;

		switch (mUriMatcher.match(uri)) {
		case TYPE_DIR:
			cursor = db.query(tableName, projection, selection, selectionArgs,
					null, null, sortOrder);
			break;
		case TYPE_ITEM:
			String id = uri.getPathSegments().get(1);
			String newSelection = "_id=" + id;
			
			if (selection != null && !"".equals(selection)) {
				newSelection += " and" + " " + selection;
			}
			cursor = db.query(tableName, projection, newSelection, selectionArgs,
					null, null, sortOrder);
			break;
		}
		if (cursor != null) {
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (mUriMatcher.match(uri) != TYPE_DIR) {
			return null;
		}

		SQLiteDatabase db = mSqliteHelper.getWritableDatabase();
		String tableName = uri.getPathSegments().get(0);
		long rowId = db.insert(tableName, "", values);

		// 获得新添加的数据的uri，作为返回值
		Uri result = ContentUris.withAppendedId(uri, rowId);

		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
