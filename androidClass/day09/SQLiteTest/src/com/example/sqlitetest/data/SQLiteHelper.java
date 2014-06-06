package com.example.sqlitetest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "EmpManage.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TABLE_DEPT = "create table dept (" +
			"id integer primary key autoincrement," +
			"d_name varchar not null)";
	private static final String CREATE_TABLE_EMP = "create table emp (" +
			"id integer primary key autoincrement," +
			"emp_name varchar not null," +
			"tel varchar null," +
			"dept_id integer not null)";
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//����һ���������
		db.beginTransaction();
		try{
			db.execSQL(CREATE_TABLE_DEPT);
			db.execSQL(CREATE_TABLE_EMP);
			//�����������������ɹ�ʱ������ǰ������Ϊ�ɹ�
			db.setTransactionSuccessful();
		}catch(Exception e){
			Log.e("create table error", e.getLocalizedMessage());
		}finally{
			//�����Ƿ���Ϊ�ɹ���ִ�лع������ύ
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
