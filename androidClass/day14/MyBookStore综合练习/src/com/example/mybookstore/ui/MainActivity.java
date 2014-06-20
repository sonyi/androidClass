package com.example.mybookstore.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mybookstore.MyApplication;
import com.example.mybookstore.R;
import com.example.mybookstore.data.DataAccess;
import com.example.mybookstore.model.Catagory;
import com.example.mybookstore.util.Literal;

public class MainActivity extends ActionBarActivity {
	
	private ArrayList<Catagory> mCatagoryArray = null;
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//获取分类信息
		MyApplication app = (MyApplication) getApplication();
		mCatagoryArray = app.getCatagoryArray();
		if (mCatagoryArray == null) {
			DataAccess dataAccess = new DataAccess(this);
			mCatagoryArray = dataAccess.queryCatagory();
			app.addArg(Literal.GET_CATAGORY_ARRAY, mCatagoryArray);
		}

		setUpViewPager();
		setUpActionBar();

		
	}

	//设置ViewPager
	private void setUpViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);

	}

	//设置Actionbar
	private void setUpActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.app_logo);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i = 0; i < mCatagoryArray.size(); i++) {
			Tab tab = actionBar.newTab();
			tab.setText(mCatagoryArray.get(i).getCatagoryName());
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	//ViewPager适配器
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {
		
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment frgmt = new TabFrame();
			Bundle data = new Bundle();
			data.putLong(Literal.FRAGMENT_KEY_VALUE, mCatagoryArray.get(arg0).getCatagory_id());
			frgmt.setArguments(data);
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.commit();
			return frgmt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCatagoryArray.size();
		}
	}

	//设置页面滑动监听器
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {//页面滑动时改变Actionbar导航位置
			ActionBar actionBar = getSupportActionBar();
			actionBar.setSelectedNavigationItem(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	//设置Actionbar的tab监听器
	private TabListener mTabListener = new TabListener() {
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			//改变tab时，相应的页面也做改变
			int position = tab.getPosition();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	};

}
