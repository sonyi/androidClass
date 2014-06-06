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
	 * 添加一个Dept部门信息到对应的数据表中
	 * @param dept
	 * @return  返回-1表示失败，否则表示新添加的行数
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
