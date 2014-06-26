package com.mymusicplay.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.mymusicplay.R;

public class PlayDetailActivity extends ActionBarActivity {
	int[] pageNum = new int[3];
	ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_detail);

		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();

		mViewPager = (ViewPager) findViewById(R.id.detail_view_pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
	}

	// ViewPager  ≈‰∆˜
	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment frgmt = null;

			switch (arg0) {
			case 0:
				frgmt = new DetailFragmentLrc();
				break;
			case 1:
				frgmt = new DetailFragmentPlayInterface();
				break;
			case 2:
				frgmt = new DetailFragmentOther();
				break;
			}

			return frgmt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pageNum.length;
		}
	}
}
