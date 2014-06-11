package com.example.actiontbartest;

import android.R.anim;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	public static final String KEY_NAME = "name";
	private String[] mListNaviItems = new String[] { "地图", "定位", "聊天" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowTitleEnabled(false);//设置不显示标题
		ArrayAdapter<String> naviListAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mListNaviItems);//设置适配器
		actionBar.setListNavigationCallbacks(naviListAdapter, mOnNaviListener);
	}

	// ActionBar的List导航方式的列表项监听
	private OnNavigationListener mOnNaviListener = new OnNavigationListener() {

		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			// TODO Auto-generated method stub
			String arg = mListNaviItems[itemPosition];
			Bundle data = new Bundle();
			data.putString(KEY_NAME, arg);
			TopLevelFragment topLevel = new TopLevelFragment();
			topLevel.setArguments(data);
			getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, topLevel).commit();
			return false;
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
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_search:
			Toast.makeText(this, "action_search", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_refresh:
			Toast.makeText(this, "action_refresh", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_settings:
			Toast.makeText(this, "action_settings", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
