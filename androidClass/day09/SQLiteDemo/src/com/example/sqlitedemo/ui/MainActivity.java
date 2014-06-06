package com.example.sqlitedemo.ui;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.R.layout;
import com.example.sqlitedemo.R.menu;
import com.example.sqlitedemo.data.DeptDataAccess;
import com.example.sqlitedemo.model.Dept;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Dept dept1 = new Dept();
		dept1.setId(1);
		dept1.setDeptName("研发部");

		Dept dept2 = new Dept();
		dept2.setId(2);
		dept2.setDeptName("市场部");

		Dept dept3 = new Dept();
		dept3.setId(3);
		dept3.setDeptName("运维部");

		DeptDataAccess deptDa = new DeptDataAccess(this);
		deptDa.addDept(dept1);
		deptDa.addDept(dept2);
		deptDa.addDept(dept3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
