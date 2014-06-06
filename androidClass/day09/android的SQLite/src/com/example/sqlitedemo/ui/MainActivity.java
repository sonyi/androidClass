package com.example.sqlitedemo.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.data.DeptDataAccess;
import com.example.sqlitedemo.model.Dept;

public class MainActivity extends Activity {
	private TextView tvData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvData = (TextView) findViewById(R.id.tv_data);
		
		DeptDataAccess deptDa = new DeptDataAccess(this);
		List<Dept> deptList = deptDa.getAllDeptList();
		
		StringBuilder strBuilder = new StringBuilder();
		for (Dept dept : deptList) {
			strBuilder.append(dept.getId()+ "    ");
			strBuilder.append(dept.getDeptName() + "\n");
		}
		String text = strBuilder.toString();
		tvData.setText(text);
		
		//
		// Dept dept1 = new Dept();
		// dept1.setId(1);
		// dept1.setDeptName("研发部");
		//
		// Dept dept2 = new Dept();
		// dept2.setId(2);
		// dept2.setDeptName("市场部");
		//
		// Dept dept3 = new Dept();
		// dept3.setId(3);
		// dept3.setDeptName("运维部");
		//
		// DeptDataAccess deptDa = new DeptDataAccess(this);
		// deptDa.addDept(dept1);
		// deptDa.addDept(dept2);
		// deptDa.addDept(dept3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
