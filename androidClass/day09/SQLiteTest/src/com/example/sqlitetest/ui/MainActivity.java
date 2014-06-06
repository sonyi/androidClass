package com.example.sqlitetest.ui;

import com.example.sqlitetest.R;
import com.example.sqlitetest.data.DeptDataAccess;
import com.example.sqlitetest.data.EmpDataAccess;
import com.example.sqlitetest.model.Dept;
import com.example.sqlitetest.model.Employee;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mEmployeeTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEmployeeTv = (TextView) findViewById(R.id.tv_emp);
		
		DeptDataAccess deptDa = new DeptDataAccess(this);
		EmpDataAccess empData = new EmpDataAccess(this);
		
		StringBuilder sb = empData.queryEmployee();
		mEmployeeTv.setText(sb);
		
		 Dept dept1 = new Dept(1, "�з���");
		 Dept dept2 = new Dept(2, "�г���");
		 Dept dept3 = new Dept(3, "��ά��");
		 deptDa.addDept(dept1);
		 deptDa.addDept(dept2);
		 deptDa.addDept(dept3);
		 
		 Employee emp1 = new Employee(1, "����", "13652165421", 1);
		 Employee emp2 = new Employee(2, "����", "13652165421", 2);
		 Employee emp3 = new Employee(3, "����", "13652165421", 1);
		 Employee emp4 = new Employee(4, "����", "13652165421", 2);
		 Employee emp5 = new Employee(5, "����", "13652165421", 2);
		 Employee emp6 = new Employee(6, "С��", "13652165421", 2);
		 empData.addEmployee(emp1);
		 empData.addEmployee(emp2);
		 empData.addEmployee(emp3);
		 empData.addEmployee(emp4);
		 empData.addEmployee(emp5);
		 empData.addEmployee(emp6);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
