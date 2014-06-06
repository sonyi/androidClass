package com.example.sqlitedemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "EmpManager.db";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_DEPT = "CREATE TABLE DEPT ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "d_name VARCHAR NOT NULL )";
	private static final String CREATE_TABLE_EMP = "CREATE TABLE EMP ("
			+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "emp_name VARCHAR NOT NULL," + "tel VARCHAR null, "
			+ "dept_id INTEGER NOT NULL )";

	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ����һ������
		db.beginTransaction();
		try {
			db.execSQL(CREATE_TABLE_DEPT);
			db.execSQL(CREATE_TABLE_EMP);
			// �����������������ɹ�ʱ������ǰ������Ϊ�ɹ�
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("create table error.", e.getLocalizedMessage());
		} finally {
			// �������񣬸����Ƿ���Ϊ�ɹ���ִ�лع����ύ
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//
	}
}
