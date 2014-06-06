package com.example.sqlitetest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitetest.model.Dept;

public class DeptDataAccess {
	private SQLiteHelper mSQLiteHelper;
	public DeptDataAccess(Context context){
		mSQLiteHelper = new SQLiteHelper(context);
	}
	
	/**
	 * ���һ��Dept������Ϣ����Ӧ�����ݱ���
	 * @param dept
	 * @return  ����-1��ʾʧ�ܣ������ʾ����ӵ�����
	 */
	public long  addDept(Dept dept){
		ContentValues cv = new ContentValues();
		cv.put("id", dept.getId());
		cv.put("d_name", dept.getDaptName());
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		long rowID = db.insert("dept", "", cv);
		db.close();
		return rowID;
	}
}
