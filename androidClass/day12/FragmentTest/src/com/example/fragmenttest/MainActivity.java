package com.example.fragmenttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity implements
		OnPerformNewsDetail {
	private FrameLayout layoutFrgmtContainer;
	public static final String KEY_TITLE_ID = "title_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layoutFrgmtContainer = (FrameLayout) findViewById(R.id.frgmt_container);
		Log.i("life", "Activity----------onCreate");
	
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 要容纳Fragment的FrameLayout如果不为空，说明当前是竖屏状态
		if (layoutFrgmtContainer != null) {
			layoutFrgmtContainer.removeAllViews();
			// 动态添加Fragment
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment titleListFrgmt = new TitleListFragment();
			ft.add(R.id.frgmt_container, titleListFrgmt).addToBackStack(null).commit();
		}
		Log.i("life", "Activity----------onResume");
		super.onResume();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void performNewsDetail(int id) {
		// TODO Auto-generated method stub
		Bundle data = new Bundle();
		data.putInt(KEY_TITLE_ID, id);
		NewsDetailFragment newsDetailFragment = null;
		if (layoutFrgmtContainer != null) {
			layoutFrgmtContainer.removeAllViews();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			newsDetailFragment = new NewsDetailFragment();
			newsDetailFragment.setArguments(data);
			ft.replace(R.id.frgmt_container, newsDetailFragment).addToBackStack(null).commit();
			
		} else {
			newsDetailFragment = (NewsDetailFragment) getSupportFragmentManager()
					.findFragmentById(R.id.frgmt_news_detail);
			newsDetailFragment.showNewsDetail(id);
		}
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("life", "Activity----------onDestroy");
		super.onDestroy();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("life", "Activity----------onPause");
		super.onPause();
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.i("life", "Activity----------onStart");
		super.onStart();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i("life", "Activity----------onStop");
		super.onStop();
	}
	
	
}
