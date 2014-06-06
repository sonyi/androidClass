package com.example.sqlitedemo.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	public Dept getDeptById(long id) {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();

		String[] columns = new String[] { "id", "d_name" };
		String selection = "id = ?";
		String[] selectionArgs = new String[] { String.valueOf(id) };

		Dept dept = null;
		Cursor c = db.query("DEPT", columns, selection, selectionArgs, null,
				null, null);
		if (c.moveToNext()) {
			dept = new Dept();
			long _id = c.getLong(c.getColumnIndexOrThrow("id"));
			String deptName = c.getString(c.getColumnIndexOrThrow("d_name"));
			dept.setId(_id);
			dept.setDeptName(deptName);
		}
		c.close();
		db.close();

		return dept;
	}

	public List<Dept> getAllDeptList() {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		String[] columns = new String[] { "id", "d_name" };
		
		List<Dept> deptList = new ArrayList<Dept>();
		Cursor c = db.query("DEPT", columns, null, null, null, null, null);
		while (c.moveToNext()) {
			Dept dept = new Dept();
			long _id = c.getLong(c.getColumnIndexOrThrow("id"));
			String deptName = c.getString(c.getColumnIndexOrThrow("d_name"));
			dept.setId(_id);
			dept.setDeptName(deptName);
			
			deptList.add(dept);
		}
		c.close();
		db.close();
		return deptList;
	}
}
