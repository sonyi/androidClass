package com.example.sqlitedemo.data;

import android.content.Context;

import com.example.sqlitedemo.model.Employee;

public class EmpDataAccess {
   private SQLiteHelper mSQLiteHelper;
   
   public EmpDataAccess(Context context) {
	   mSQLiteHelper = new SQLiteHelper(context);
   }
	
   public long addEmp(Employee emp) {
	   return 0;
   }
}
