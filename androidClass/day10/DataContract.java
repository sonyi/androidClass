package com.example.sqlitedemo.data;

public final class DataContract {
	public static final class DeptContract {
		public static final String TABLE_NAME = "DEPT";

		public static final String ID = "id";
		public static final String DEPT_NAME = "d_name";
	}
	
	public static final class EmpContract {
		public static final String TABLE_NAME = "EMP";
		
		public static final String ID = "id";
		public static final String EMP_NAME = "emp_name";
		public static final String TEL = "tel";
		public static final String DEPT_ID = "dept_id";
	}
}
