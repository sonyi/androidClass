package com.example.photomanagetest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "PhotoManageData.db";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_PHOTEMANAGE = "create table photoData (" +
			"id integer primary key," +
			"imgTitle varchar," +
			"imgPath varchar not null," +
			"imgTime varchar not null )";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub	
		db.execSQL(CREATE_TABLE_PHOTEMANAGE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
