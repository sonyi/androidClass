package com.example.sqlitetest.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	public StringBuilder queryDept(){
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = {"id","d_name"};
		Cursor c = db.query("dept", columns, null, null, null, null, null);
		StringBuilder sb = null;
		while(c.moveToNext()){
			if(sb == null){
				sb = new StringBuilder();
				sb.append("id				");
				sb.append("����\r\n");
			}
			long id = c.getLong(c.getColumnIndexOrThrow("id"));
			String deptName = c.getString(c.getColumnIndexOrThrow("d_name"));
			sb.append(id + "			");
			sb.append(deptName + "\r\n");
		}
		c.close();
		db.close();
		return sb;
		
	}
}
