package com.example.mybookstore.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.model.Catagory;

public class MainActivity extends ActionBarActivity {
	private ArrayList<Catagory> mCatagoryArray = null;
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DataAccess dataAccess = new DataAccess(this);
		mCatagoryArray = dataAccess.queryCatagory();
		
		setUpViewPager();
		setUpActionBar();

	}

	private void setUpViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		
	}
	
	private void setUpActionBar() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int i = 0; i < mCatagoryArray.size(); i++){
			Tab tab = actionBar.newTab();
			tab.setText(mCatagoryArray.get(i).getCatagoryName());
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
			
		}
	}
	
	private class ViewPagerAdapter extends FragmentStatePagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return new TabFrame();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCatagoryArray.size();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
