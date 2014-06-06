package com.example.sqlitedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitedemo.model.Dept;

public class DeptDataAccess {
	private SQLiteHelper mSQLiteHelper;

	public DeptDataAccess(Context context) {
		mSQLiteHelper = new SQLiteHelper(context);
	}

	/**
	 * ���һ��Dept������Ϣ����Ӧ�����ݿ��
	 * 
	 * @param dept
	 * @return ����-1��ʾ����ʧ�ܣ������ʾ����ӵ��е�id
	 */
	public long addDept(Dept dept) {
		ContentValues cv = new ContentValues();
		cv.put("id", dept.getId());
		cv.put("d_name", dept.getDeptName());

		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		long rowId = db.insert("DEPT", "", cv);
		db.close();
		return rowId;
	}
}
