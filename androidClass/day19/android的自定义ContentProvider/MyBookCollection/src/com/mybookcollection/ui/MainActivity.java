package com.mybookcollection.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.mybookcollection.R;
import com.mybookcollection.data.DataUtil;
import com.mybookcollection.model.BookCatagory;
import com.mybookcollection.util.ToastUtil;

public class MainActivity extends ActionBarActivity {
	private ViewPager mViewPager;
	private BookCatagoryAdapter mViewPagerAdapter;
	private List<BookCatagory> mCatagories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 调用数据访问类，获取图书的类别信息集合
		mCatagories = new DataUtil(this).getBookCatagories();

		initialViewPager();
		initialActionBar();
	}

	private void initialActionBar() {
		// 设定ActionBar的导航模式为Tab导航
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowHomeEnabled(false);

		// 添加Tab标签项
		for (BookCatagory catagory : mCatagories) {
			Tab tab = actionBar.newTab();
			tab.setText(catagory.getCatagoryName());
			tab.setTabListener(mTabListener);
			actionBar.addTab(tab);
		}
	}

	private void initialViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPagerAdapter = new BookCatagoryAdapter();
		mViewPager.setPageMargin(16);
		mViewPager.setAdapter(mViewPagerAdapter);
		mViewPager.setOnPageChangeListener(mViewPagerListener);
	}

	// ViewPager适配器
	private class BookCatagoryAdapter extends FragmentStatePagerAdapter {
		public BookCatagoryAdapter() {
			super(getSupportFragmentManager());
		}

		@Override
		public int getCount() {
			return mCatagories.size();
		}

		@Override
		public Fragment getItem(int position) {
			BookListFragment frgmt = new BookListFragment();
			BookCatagory catagory = mCatagories.get(position);

			Bundle args = new Bundle();
			args.putLong("ctgrId", catagory.getId());
			frgmt.setArguments(args);

			return frgmt;
		}
	};

	// ViewPager切换页面时的监听器
	private OnPageChangeListener mViewPagerListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			getSupportActionBar().setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int position) {
		}
	};

	// Tab监听器
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
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		SearchView view = (SearchView) MenuItemCompat.getActionView(menu
				.findItem(R.id.action_search));
		view.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String text) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String textt) {
				ToastUtil.showToast(MainActivity.this, "搜索框正在工作...");
				return true;
			}
		});

		return true;
	}
}
