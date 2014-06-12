package com.actionbardemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class LowLevelActivity extends ActionBarActivity {
	private String[] mListNaviItems = new String[] { "地图", "定位", "聊天" };

	private ViewPager viewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_low_level);
		initialActionBar();
		initialViewPager();
	}

	// 添加ActionBar的Tabs (标签页)
	private void initialActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (String text : mListNaviItems) {
			Tab tab = actionBar.newTab();
			tab.setText(text);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	private TabListener mTabListener = new TabListener() {
		@Override
		public void onTabSelected(Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			//
		}

		@Override
		public void onTabUnselected(Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			//
		}

		@Override
		public void onTabReselected(Tab tab,
				android.support.v4.app.FragmentTransaction ft) {
			//
		}
	};

	private void initialViewPager() {
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mViewPagerAdapter);
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return mListNaviItems.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment frgmt = null;
			switch (position) {
			case 0:
				frgmt = new LowLevelMapFragment();
				break;
			case 1:
				frgmt = new LowLevelLocationFragment();
				break;
			case 2:
				frgmt = new LowLevelChatFragment();
				break;
			}
			return frgmt;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.low_level, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// if (item.getItemId() == android.R.id.home) {
		// finish();
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}
}
