package com.actionbardemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity {
	public static final String KEY_NAME = "name";

	private String[] mListNaviItems = new String[] { "地图", "定位", "聊天" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getSupportActionBar();
		// 设置ActionBar不显示标题
		actionBar.setDisplayShowTitleEnabled(false);
		// 把ActionBar导航方式指定为List导航
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> naviListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListNaviItems);
		// 设置List导航的两个回调实例,第一个是列表项适配器，第二个导航项单击事件监听
		actionBar.setListNavigationCallbacks(naviListAdapter, mOnNaviListener);
	}

	// ActionBar的List导航方式的列表项监听
	private OnNavigationListener mOnNaviListener = new OnNavigationListener() {

		@Override
		public boolean onNavigationItemSelected(int position, long id) {
			String arg = mListNaviItems[position];
			Bundle data = new Bundle();
			data.putString(KEY_NAME, arg);

			TopLevelFragment topLevel = new TopLevelFragment();
			topLevel.setArguments(data);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.layout_container, topLevel).commit();

			return true;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			//
			break;
		case R.id.action_refresh:
			//
			break;
		case R.id.action_settings:
			//
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
