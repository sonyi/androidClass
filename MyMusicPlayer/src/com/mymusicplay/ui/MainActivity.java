package com.mymusicplay.ui;

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

import com.mymusicplay.MusicPlayApplication;
import com.mymusicplay.R;

public class MainActivity extends ActionBarActivity {
	private String[] mMusicCatagory = new String[] { "我的音乐", "专辑", "最常听" };
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new MusicPlayApplication();
		
		setUpViewPager();
		setUpActionBar();
	}

	private void setUpViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}

	// 设置Actionbar
	private void setUpActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setIcon(R.drawable.ic_default_art);
		actionBar.setTitle("自己的播放器");

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i = 0; i < mMusicCatagory.length; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(mMusicCatagory[i]);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	// ViewPager适配器
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment frgmt = null;
			switch (arg0) {
			case 0:
				frgmt = new FragmentTabMusic();
				break;
			case 1:
				frgmt = new FragmentTabAlbum();
				break;
			case 2:
				frgmt = new FragmentTabLike();
				break;
			}
			
			// Bundle data = new Bundle();
			// frgmt.setArguments(data);
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.commit();
			return frgmt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMusicCatagory.length;
		}
	}

	// 设置页面滑动监听器
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {// 页面滑动时改变Actionbar导航位置
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

	// 设置Actionbar的tab监听器
	private TabListener mTabListener = new TabListener() {
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// 改变tab时，相应的页面也做改变
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
