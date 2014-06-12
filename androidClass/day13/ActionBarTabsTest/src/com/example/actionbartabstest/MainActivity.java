package com.example.actionbartabstest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private Fragment mFrgmtA = new TabsFrameA();
	private Fragment mFrgmtB = new TabsFrameB();
	private Fragment mFrgmtC = new TabsFrameC();

	private static final int TAB_INDEX_COUNT = 3;
	private static final int TAB_INDEX_ONE = 1;
	private static final int TAB_INDEX_TWO = 2;
	private static final int TAB_INDEX_THREE = 3;

	private ViewPager mViewPager;

	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);

		setUpActionBar();
		setUpViewPager();
		setUpTabs();
	}

	private void setUpActionBar() {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	}

	private void setUpViewPager() {
		// TODO Auto-generated method stub
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						super.onPageSelected(position);
						final ActionBar actionBar = getSupportActionBar();
						actionBar.setNavigationMode(position);
					}

					@Override
					public void onPageScrollStateChanged(int state) {
						// TODO Auto-generated method stub
						switch (state) {
						case ViewPager.SCROLL_STATE_IDLE:
							// TODO
							break;
						case ViewPager.SCROLL_STATE_DRAGGING:
							// TODO
							break;
						case ViewPager.SCROLL_STATE_SETTLING:
							// TODO
							break;
						default:
							// TODO
							break;
						}
					}
				});
	}
	
	 private void setUpTabs() {
	    	final ActionBar actionBar = getSupportActionBar();
	    	for (int i = 0; i < mViewPagerAdapter.getCount(); ++i) {
	    		actionBar.addTab(actionBar.newTab()
	    				.setText(mViewPagerAdapter.getPageTitle(i))
	    				.setTabListener(this));
	    	}
	    }
	

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub

			switch (arg0) {
			case TAB_INDEX_ONE:
				return mFrgmtA;
			case TAB_INDEX_TWO:
				return mFrgmtB;
			case TAB_INDEX_THREE:
				return mFrgmtC;
			}
			throw new IllegalStateException("No fragment at position " + arg0);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return TAB_INDEX_COUNT;
		}
	}

	private ActionBar getSupportActionBar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTabReselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}
