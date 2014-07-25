package com.mymusicplay.data;

import com.mymusicplay.util.Const;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "Lrc.db";
	private static final int DATABASE_VERSION = 1;

	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 创建分类表的SQL语句
		private static final String CREATE_LRC_TABLE = "CREATE TABLE "
				+ Const.LRC_TABLE + " ( "
				+ Const.MUSIC_NAME + " VARCHAR NOT NULL," 
				+ Const.LRC_URL + " VARCHAR NOT NULL)";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_LRC_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
