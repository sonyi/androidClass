package com.example.homeactivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView mInfoList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInfoList = (ListView) findViewById(R.id.lv_info);
		View headView = getLayoutInflater().inflate(R.layout.view_info_header, null);
		mInfoList.addHeaderView(headView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
