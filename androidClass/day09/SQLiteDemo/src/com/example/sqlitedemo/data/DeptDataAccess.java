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
	 * 添加一个Dept部门信息到对应的数据库表
	 * 
	 * @param dept
	 * @return 返回-1表示插入失败，否则表示新添加的行的id
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
