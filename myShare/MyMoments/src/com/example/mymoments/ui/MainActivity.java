package com.example.mymoments.ui;

import com.example.mymoments.R;

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

public class MainActivity extends ActionBarActivity {
	private String[] mCatagory = new String[] { "��ҳ��", "��ʷ��¼", "����" };
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViewPager();
		setUpActionBar();
	}

	// ����ViewPager
	private void setUpViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);

	}

	// ����Actionbar
	private void setUpActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		int count = mCatagory.length;
		for (int i = 0; i < count; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(mCatagory[i]);
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	// ViewPager������
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
			frgmt = new TabFrameLeft();
				break;
			case 1:
				frgmt = new TabFrameCenter();
				break;
			case 2:
				frgmt = new TabFrameRight();
				break;
			}
			return frgmt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCatagory.length;
		}
	}

	// ����ҳ�滬��������
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {// ҳ�滬��ʱ�ı�Actionbar����λ��
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

	// ����Actionbar��tab������
	private TabListener mTabListener = new TabListener() {

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			int position = arg0.getPosition();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
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
