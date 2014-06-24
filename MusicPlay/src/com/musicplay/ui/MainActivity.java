package com.musicplay.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.musicplay.MusicPlayApplication;
import com.musicplay.service.IPlayBackService;
import com.musicplaytesty.R;


public class MainActivity extends ActionBarActivity {
	
	String[] mTabs = new String[]{"专辑","曲目"};
	private ViewPager mViewPager;
	private MusicAdapter mMusicAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViewPager();
		initTabs();
		
		
	}
	//初始化ViewPager
	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mMusicAdapter = new MusicAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mMusicAdapter);
		mViewPager.setOnPageChangeListener(mViewPagerListener);
	}
	//初始化Tabs
	private void initTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("我的曲库");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int i = 0;i<mTabs.length; i++ ) {
			Tab tab = actionBar.newTab();
			tab.setText(mTabs[i]);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
		
	}
	// ViewPager适配器
	class MusicAdapter extends FragmentStatePagerAdapter {

		public MusicAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		@Override
		public int getCount() {
			return mTabs.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch(position) {
			case 0 :
				fragment = new AlbumFragment();
				break;
			case 1 :
				fragment = new TracksFragment();
				break;
			}
			
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.commit();
				
			return fragment;
		}

	}
	// ViewPager切换页面时的监听器
	private OnPageChangeListener mViewPagerListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
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
	// Tabs切换时的监听器
	private TabListener mTabListener = new TabListener() {

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			int position = tab.getPosition();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub

		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
