package com.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {
	private FrameLayout layoutFrgmtContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layoutFrgmtContainer = (FrameLayout) findViewById(R.id.frgmt_container);
		
		// 要容纳Fragment的FrameLayout如果不为空，说明当前是竖屏状态
		if (layoutFrgmtContainer != null) {
			// 动态添加Fragment
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			Fragment titleListFrgmt = new TitleListFragment();
			ft.add(R.id.frgmt_container, titleListFrgmt).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
