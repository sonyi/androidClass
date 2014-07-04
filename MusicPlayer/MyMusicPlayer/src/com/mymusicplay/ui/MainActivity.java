package com.mymusicplay.ui;

import android.app.NotificationManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.Menu;
import android.view.MenuItem;

import com.mymusicplay.R;
import com.mymusicplay.util.Const;

public class MainActivity extends BaseActivity {
	private String[] mMusicCatagory = new String[] { "我的音乐", "专辑", "最喜欢" };
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void initialWidgets() {
		setContentView(R.layout.activity_main);
		setUpViewPager();
		setUpActionBar();
	}

	private void setUpViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}

	// 初始化actionbar
	private void setUpActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setIcon(R.drawable.ic_default_art);
		actionBar.setTitle("我的音乐播放器");

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i = 0; i < mMusicCatagory.length; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(mMusicCatagory[i]);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	// ViewPager
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
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

			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.commit();
			return frgmt;
		}

		@Override
		public int getCount() {
			return mMusicCatagory.length;
		}
	}

	// 
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {//
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

	// 
	private TabListener mTabListener = new TabListener() {
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// 
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_exit:
			//
			NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			nm.cancel(Const.NOTIFICATION_ID);
			System.exit(0);//
			break;
		case R.id.action_settings:

			break;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
