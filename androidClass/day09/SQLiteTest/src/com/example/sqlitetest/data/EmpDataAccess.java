package com.example.sqlitetest.data;

import java.util.ArrayList;

import com.example.sqlitetest.model.Employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EmpDataAccess {
	private SQLiteHelper mSQLiteHelper;
	public EmpDataAccess(Context context){
		mSQLiteHelper = new SQLiteHelper(context);
	}
	/**
	 * ���һ��employee��Ϣ�����ڵ����ݿ����
	 * @param emp
	 * @return ����-1��ʾ���ʧ�ܣ����򷵻ظ�����е��к�
	 */
	public long addEmployee(Employee emp){
		ContentValues cv = new ContentValues();
		cv.put("id", emp.getId());
		cv.put("emp_name", emp.getEmpName());
		cv.put("tel", emp.getTel());
		cv.put("dept_id", emp.getDept_id());
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		long rowID = db.insert("emp", null, cv);
		db.close();
		return rowID;
	}
	
	public StringBuilder queryEmployee(){
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String[] columns = {"id","emp_name","tel","dept_id"};
		Cursor cursor = db.query("emp", columns, null, null, null, null, null);
		StringBuilder sb = null;
		 while(cursor.moveToNext()){
			 if(sb == null){
				 sb = new StringBuilder();
				 sb.append("id		");
				 sb.append("����				");
				 sb.append("�绰����				");
				 sb.append("���ű��\r\n");
			 }
			 Employee emp = new Employee();
			 long id = cursor.getLong(cursor.getColumnIndex("id"));
			 String empName = cursor.getString(cursor.getColumnIndex("emp_name"));
			 String tel = cursor.getString(cursor.getColumnIndex("tel"));
			 long dept_id = cursor.getLong(cursor.getColumnIndex("dept_id"));
			 sb.append(id + "			");
			 sb.append(empName + "			");
			 sb.append(tel + "  			");
			 sb.append(dept_id + "\r\n");
		 }
		cursor.close();
		db.close();
		return sb;
	}
}
