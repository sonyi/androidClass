package com.fragmenttabhostdemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	public static final String KEY_NAME = "name";

	private FragmentTabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialTabs();
	}

	private void initialTabs() {
		tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
		// 设置FragmentTabHost
		tabHost.setup(this, getSupportFragmentManager(), R.id.layout_container);

		// 构建并添加每个Tab
		TabSpec tabChat = tabHost.newTabSpec("chat");
		View tabChatView = getIndicatorView("聊天", R.drawable.social_chat);
		tabChat.setIndicator(tabChatView);

		// 指定传给对应的Fragment的参数
		Bundle chatArg = new Bundle();
		chatArg.putString(KEY_NAME, "聊天");
		// 参数1：标签项，参数2：对应的Fragment的类，参数2：传递给Fragment的Bundle参数
		tabHost.addTab(tabChat, TopLevelFragment.class, chatArg);

		TabSpec tabFriends = tabHost.newTabSpec("friends");
		View tabFriendsView = getIndicatorView("好友", R.drawable.social_bcc);
		tabFriends.setIndicator(tabFriendsView);
		Bundle friendsArg = new Bundle();
		friendsArg.putString(KEY_NAME, "好友");
		tabHost.addTab(tabFriends, TopLevelFragment.class, friendsArg);

		TabSpec tabMap = tabHost.newTabSpec("map");
		View tabMapView = getIndicatorView("地图", R.drawable.location_map);
		tabMap.setIndicator(tabMapView);
		Bundle mapArg = new Bundle();
		mapArg.putString(KEY_NAME, "地图");
		tabHost.addTab(tabMap, TopLevelFragment.class, mapArg);

		TabSpec tabLocation = tabHost.newTabSpec("location");
		View tabLocView = getIndicatorView("定位", R.drawable.location_place);
		tabLocation.setIndicator(tabLocView);
		Bundle LocationArg = new Bundle();
		LocationArg.putString(KEY_NAME, "定位");
		tabHost.addTab(tabLocation, TopLevelFragment.class, LocationArg);
	}

	private View getIndicatorView(String text, int drawableId) {
		View view = getLayoutInflater().inflate(R.layout.view_tab_item, null);
		TextView tvTabText = (TextView) view.findViewById(R.id.tv_tab_text);
		tvTabText.setText(text);
		Drawable top = getResources().getDrawable(drawableId);
		tvTabText.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);

		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
