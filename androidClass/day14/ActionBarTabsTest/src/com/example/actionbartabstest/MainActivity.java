package com.example.actionbartabstest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	private String[] mListNaviItems = new String[] { "地图", "定位", "聊天" };
	
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);	
		setUpViewPager();
		setUpActionBar();

	}

	private void setUpActionBar() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		for (int i = 0; i < mListNaviItems.length; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(mListNaviItems[i]);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
			
		}

	}

	private void setUpViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}


	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment frgmt = null;
			switch (arg0) {
			case 0:
				frgmt = new TabsFrameA();
				break;
			case 1:
				frgmt = new TabsFrameB();
				break;
			case 2:
				frgmt = new TabsFrameC();
				break;
			}
			return frgmt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListNaviItems.length;
		}
	}

	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			ActionBar actionBar = getSupportActionBar();
			actionBar.setSelectedNavigationItem(arg0);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};


	private TabListener mTabListener = new TabListener() {
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			//
			int position = tab.getPosition();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			//
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			//
		}
	};

}
